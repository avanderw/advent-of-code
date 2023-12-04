$directories = Get-ChildItem -Directory -Recurse
$yearDirectories = $directories | Where-Object { $_.Name -match "^\d{4}$" }

$languageLinks = @{}
foreach ($yearDirectory in $yearDirectories) {
    $dayDirectories = Get-ChildItem -Path $yearDirectory.FullName -Directory

    foreach ($dayDirectory in $dayDirectories) {
        $languageDirectories = Get-ChildItem -Path $dayDirectory.FullName -Directory

        foreach ($languageDirectory in $languageDirectories) {
            if (!$languageLinks.ContainsKey($languageDirectory.BaseName)) {
                $languageLinks[$languageDirectory.BaseName] = @{}
            }

            if (!$languageLinks[$languageDirectory.BaseName].ContainsKey($yearDirectory.BaseName)) {
                $languageLinks[$languageDirectory.BaseName][$yearDirectory.BaseName] = @()
            }
             
            $languageLinks[$languageDirectory.BaseName][$yearDirectory.BaseName] += $languageDirectory
        }
    }
}

Write-Output "# Advent of Code Solutions`n"
Write-Output "This repository contains my solutions to the [Advent of Code](https://adventofcode.com/) challenges."

foreach ($lang in $languageLinks.Keys) {
    Write-Output "`n## $lang"
    foreach ($year in $languageLinks[$lang].Keys) {
        Write-Output "`n### $year`n"
        foreach ($day in $languageLinks[$lang][$year]) {
            if (Test-Path -Path "$($day.FullName)/README.md") {
                # Read the contents of the README.md file
                $fileContent = Get-Content -Path "$($day.FullName)\README.md" -Raw

                # Extract the first header line (lines starting with one or more # characters)
                $firstHeader = $fileContent -match '^#+ (.+)' | Out-Null
                $firstHeader = $matches[1]

                if ($firstHeader) {
                    $firstHeader = $firstHeader.Replace("`n", "")
                    $firstHeader = $firstHeader.Replace("`r", "")
                } else {
                    $firstHeader = "The README.md file does not contain any headers."
                }
            } else {
                $firstHeader = "No README.md file found."
            }

            Write-Output "- [$firstHeader]($year/$($day.parent.BaseName)/$lang)"
        }
    }
}
