function Get-JavaDaAplicacao {
    $candidatos = [System.Collections.Generic.List[string]]::new()
    if ($env:JAVA_HOME) { $candidatos.Add((Join-Path $env:JAVA_HOME 'bin\java.exe')) }
    foreach ($base in @((Join-Path $env:ProgramFiles 'Java'), (Join-Path $env:ProgramFiles 'Eclipse Adoptium'))) {
        if (Test-Path -LiteralPath $base) {
            Get-ChildItem -LiteralPath $base -Directory -Filter 'jdk*' | Sort-Object Name -Descending | ForEach-Object {
                $candidatos.Add((Join-Path $_.FullName 'bin\java.exe'))
            }
        }
    }
    $comando = Get-Command java -ErrorAction SilentlyContinue
    if ($comando -and $comando.Source -notlike '*\Common Files\Oracle\Java\javapath\*') { $candidatos.Add($comando.Source) }
    foreach ($candidato in $candidatos) {
        if (!(Test-Path -LiteralPath $candidato)) { continue }
        # Java escreve a versao em stderr. Ler pelo processo evita que o
        # Windows PowerShell 5.1 transforme essa saida normal em uma excecao.
        $processoJava = [System.Diagnostics.Process]::new()
        try {
            $processoJava.StartInfo.FileName = $candidato
            $processoJava.StartInfo.Arguments = '-version'
            $processoJava.StartInfo.UseShellExecute = $false
            $processoJava.StartInfo.CreateNoWindow = $true
            $processoJava.StartInfo.RedirectStandardOutput = $true
            $processoJava.StartInfo.RedirectStandardError = $true
            $null = $processoJava.Start()
            $saidaJava = $processoJava.StandardOutput.ReadToEndAsync()
            $erroJava = $processoJava.StandardError.ReadToEndAsync()
            if (!$processoJava.WaitForExit(10000)) {
                $processoJava.Kill()
                continue
            }
            $versao = $saidaJava.Result + $erroJava.Result
            $codigoJava = $processoJava.ExitCode
        } finally { $processoJava.Dispose() }
        if ($codigoJava -eq 0 -and $versao -match 'version "(\d+)') {
            if ([int]$Matches[1] -ge 17) {
                $env:JAVA_HOME = Split-Path (Split-Path $candidato -Parent) -Parent
                return $candidato
            }
        }
    }
    throw 'Java 17 ou superior não encontrado. Configure JAVA_HOME com a pasta do JDK instalado.'
}
