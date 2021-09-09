# Cognitive Complexity Test Task

![Build](https://github.com/kkarnauk/cognitive-complexity-test-task/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## Description
<!-- Plugin description -->
It's a test task for Fall 2021 Practice in JetBrains for the project 'Cognitive Complexity Plugin for IntelliJ-IDEA'.

This plugin is just a set of some dialogs and notifications. Supported shortcuts:
- `Shift + Ctrl + Alt + Quote`: open an info dialog with the current file name and the current project name.
- `Shift + Ctrl + Alt + Semicolon`: show a notification with the type of the current file.
- `Shift + Ctrl + Alt + OpenBracket`: show an info dialog with the name of the current method (under the caret) and its length in lines.
  Also, you can press the button 'Add comment' and this info will be added as a comment for the method.

If there is no current file or method, then you'll see an error like `No current file!` instead of a name.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "cognitive-complexity-test-task"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/kkarnauk/cognitive-complexity-test-task/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
