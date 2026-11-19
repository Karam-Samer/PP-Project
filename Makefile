SHELL := cmd.exe
PWSH  := pwsh -NoProfile -ExecutionPolicy Bypass -File

.PHONY: build clear test run prompt

build:
	@$(PWSH) tools/ps1/build.ps1

clear:
	@$(PWSH) tools/ps1/clear.ps1

test:
	@$(PWSH) tools/ps1/test.ps1 -TestClass "$(word 2,$(MAKECMDGOALS))"

run:
	@$(PWSH) tools/ps1/run.ps1 -Mode "$(word 2,$(MAKECMDGOALS))"

prompt:
	@$(PWSH) tools/ps1/prompt.ps1

# Ignore positional arguments passed to Make
%:
	@:
