param(
    [Parameter(Mandatory = $true)]
    [ValidateSet("p", "s")]
    [string]$Mode
)

$ErrorActionPreference = "Stop"

# Project paths
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
Set-Location $ProjectRoot

$Dist = Join-Path $ProjectRoot "dist"

# Build application first
& (Join-Path $PSScriptRoot "build.ps1")

# Select main class
$MainClass = switch ($Mode) {
    "p" { "app.ParallelMain" }
    "s" { "app.SequentialMain" }
}

# Execute application
& java -cp $Dist $MainClass

if ($LASTEXITCODE -ne 0) {
    throw "Application execution failed!"
}
