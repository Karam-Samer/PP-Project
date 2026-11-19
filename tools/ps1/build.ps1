$ErrorActionPreference = "Stop"

# Project paths
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
Set-Location $ProjectRoot

$SourceRoot = Join-Path $ProjectRoot "src"
$AppSource  = Join-Path $SourceRoot "app"
$Dist       = Join-Path $ProjectRoot "dist"

New-Item -ItemType Directory -Force -Path $Dist | Out-Null

# Discover all application Java files
$AppFiles = @(Get-ChildItem -Path $AppSource -Filter "*.java" -File -Recurse)

$ChangedFiles = @()

foreach ($File in $AppFiles) {

    # Preserve the package structure inside dist
    $RelativePath = [System.IO.Path]::GetRelativePath(
        $SourceRoot,
        $File.FullName
    )

    $ExpectedClass = Join-Path $Dist (
        [System.IO.Path]::ChangeExtension($RelativePath, ".class")
    )

    $NeedsCompilation = $true

    if (Test-Path $ExpectedClass) {

        $SourceTime = $File.LastWriteTime
        $ClassTime  = (Get-Item $ExpectedClass).LastWriteTime

        if ($ClassTime -ge $SourceTime) {
            $NeedsCompilation = $false
        }
    }

    if ($NeedsCompilation) {
        $ChangedFiles += $File.FullName
    }
}

# Compile only changed application files
if ($ChangedFiles.Count -gt 0) {

    & javac -d $Dist -cp $Dist -sourcepath $SourceRoot $ChangedFiles

    if ($LASTEXITCODE -ne 0) {
        throw "Application compilation failed!"
    }
}
