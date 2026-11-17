$MavenArgs = @($args)
$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'scripts\Java.ps1')
$null = Get-JavaDaAplicacao
$cache = Join-Path $PSScriptRoot '.cache'
$maven = Join-Path $cache 'apache-maven-3.9.9\bin\mvn.cmd'
if (!(Test-Path -LiteralPath $maven)) {
    $url = 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip'
    $sha = '8beac8d11ef208f1e2a8df0682b9448a9a363d2ad13ca74af43705549e72e74c9378823bf689287801cbbfc2f6ea9596201d19ccacfdfb682ee8a2ff4c4418ba'
    Write-Host 'Baixando Maven 3.9.9 portátil: 9.202.456 bytes (9,20 MB), Apache/Maven Central.'
    Write-Host "Origem: $url"
    Write-Host 'O arquivo será conferido por SHA-512 antes da extração. Nenhum instalador será executado.'
    New-Item -ItemType Directory -Force -Path $cache | Out-Null
    $zip = Join-Path $cache 'apache-maven-3.9.9-bin.zip'
    Invoke-WebRequest -Uri $url -OutFile $zip
    if ((Get-FileHash -LiteralPath $zip -Algorithm SHA512).Hash -ne $sha) { throw 'Checksum do Maven inválido. Extração cancelada.' }
    Expand-Archive -LiteralPath $zip -DestinationPath $cache -Force
}
$repo = if ($env:TAREFAS_MAVEN_REPO) { $env:TAREFAS_MAVEN_REPO } else { Join-Path $cache 'm2' }
Push-Location $PSScriptRoot
try {
    & $maven '-C' "-Dmaven.repo.local=$repo" @MavenArgs
    if ($LASTEXITCODE -ne 0) { throw "Maven terminou com código $LASTEXITCODE." }
} finally { Pop-Location }
