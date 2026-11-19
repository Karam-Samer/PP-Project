param(
    [Parameter(Mandatory = $true)]
    [string]$TestClass
)

$ErrorActionPreference = "Stop"

# Project paths
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
Set-Location $ProjectRoot

$TestSource = Join-Path $ProjectRoot "src\test"
$Dist       = Join-Path $ProjectRoot "dist"
$TestOutput = Join-Path $Dist "test"

# Normalize input
$TestClass = [System.IO.Path]::GetFileNameWithoutExtension($TestClass)

$TestFile      = Join-Path $TestSource "$TestClass.java"
$TestClassFile = Join-Path $TestOutput "$TestClass.class"

# Build application first
& (Join-Path $PSScriptRoot "build.ps1")

# Validate test file
if (-not (Test-Path $TestFile)) {
    throw "Test file not found: $TestFile"
}

New-Item -ItemType Directory -Force -Path $TestOutput | Out-Null

# Check whether test compilation is necessary
$NeedsCompilation = $true

if (Test-Path $TestClassFile) {

    $SourceTime = (Get-Item $TestFile).LastWriteTime
    $ClassTime  = (Get-Item $TestClassFile).LastWriteTime

    if ($ClassTime -ge $SourceTime) {
        $NeedsCompilation = $false
    }
}

# Compile test if needed
if ($NeedsCompilation) {

    & javac -cp $Dist -d $TestOutput $TestFile

    if ($LASTEXITCODE -ne 0) {
        throw "Test compilation failed!"
    }
}

# Execute test
& java -cp "$Dist;$TestOutput" $TestClass

if ($LASTEXITCODE -ne 0) {
    throw "Test execution failed!"
}
