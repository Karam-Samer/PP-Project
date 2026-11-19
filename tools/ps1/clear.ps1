$ErrorActionPreference = "Stop"

# Project paths
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$Dist = Join-Path $ProjectRoot "dist"

# Remove generated output
if (Test-Path $Dist) {
    Remove-Item -Path $Dist -Recurse -Force
}

# Recreate empty dist directory
New-Item -ItemType Directory -Path $Dist -Force | Out-Null
