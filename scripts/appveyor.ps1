Add-Type -AssemblyName System.IO.Compression.FileSystem

# SBT

$sbtZipPath = "$($env:USERPROFILE)\sbt-0.13.12.zip"

if (!(Test-Path -Path $sbtZipPath)) {
    Write-Host "Downloading sbt.." -ForegroundColor Cyan
    (new-object System.Net.WebClient).DownloadFile('https://dl.bintray.com/sbt/native-packages/sbt/0.13.12/sbt-0.13.12.zip', $sbtZipPath)
}

Write-Host "Installing sbt..."
[System.IO.Compression.ZipFile]::ExtractToDirectory($sbtZipPath, "C:\")

Write-Host "sbt installed" -ForegroundColor Green

# Node
Update-NodeJsInstallation$env:nodejs_version

npm install