$ErrorActionPreference = "Stop"

# Project paths
$ProjectRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
Set-Location $ProjectRoot

$DocumentationDir = Join-Path $ProjectRoot "tools\documentation"
$PromptFile = Join-Path $DocumentationDir "prompt.txt"

# Create documentation directory and output file
New-Item -ItemType Directory -Force -Path $DocumentationDir | Out-Null
New-Item -ItemType File -Force -Path $PromptFile | Out-Null

# Ignore Git internals and the generated prompt itself
$Files = @(
    Get-ChildItem -Path $ProjectRoot -File -Recurse |
    Where-Object {
        $_.FullName -notmatch '[\\/]\.git[\\/]' -and
        $_.FullName -ne $PromptFile
    }
)

# ==========================================
# 1. Project tree
# ==========================================

$Tree = (& "$env:WINDIR\System32\tree.com" /f | Out-String).TrimEnd()

$Lines = [System.Collections.Generic.List[string]]::new()

$Lines.Add("PROJECT TREE")
$Lines.Add("============")
$Lines.Add($Tree)
$Lines.Add("")
$Lines.Add("")


# ==========================================
# 2. Non-Java file metadata
# ==========================================

$Lines.Add("NON-JAVA FILE METADATA")
$Lines.Add("======================")
$Lines.Add("")

$NonJavaFiles = @($Files | Where-Object { $_.Extension -ne ".java" })

foreach ($File in $NonJavaFiles) {

    $RelativePath = [System.IO.Path]::GetRelativePath(
        $ProjectRoot,
        $File.FullName
    )

    $SizeKB = [math]::Round($File.Length / 1KB, 2)

    $Lines.Add("FILE: $RelativePath")
    $Lines.Add("Type: $($File.Extension)")
    $Lines.Add("Size: $SizeKB KB ($($File.Length) bytes)")
    $Lines.Add("Last modified: $($File.LastWriteTime)")
    $Lines.Add("")
}


# ==========================================
# 3. Java source contents
# ==========================================

$Lines.Add("")
$Lines.Add("JAVA SOURCE FILES")
$Lines.Add("=================")
$Lines.Add("")

$JavaFiles = @($Files | Where-Object { $_.Extension -eq ".java" })

foreach ($File in $JavaFiles) {

    $RelativePath = [System.IO.Path]::GetRelativePath(
        $ProjectRoot,
        $File.FullName
    )

    $Lines.Add("FILE: $RelativePath")
    $Lines.Add("====================")
    $Lines.Add("")

    $Content = Get-Content -LiteralPath $File.FullName -Raw

    $Lines.Add($Content)
    $Lines.Add("")
    $Lines.Add("")
}


# ==========================================
# 4. Write prompt file
# ==========================================

$Lines -join "`r`n" |
    Set-Content -LiteralPath $PromptFile -Encoding utf8
