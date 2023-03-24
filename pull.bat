@echo on

set "url=aaa 123.avc.bvf"

setlocal enabledelayedexpansion

for /f "delims=" %%i in ('git remote -v') do (
    for %%a in (%%i) do (
        if not %%a equ " " (
            set t=%t% !a!
        )
        echo !t!
    )
)