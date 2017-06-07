## Welcome to AbyssalCraft on Github

### Main information

The mod thread can be located at
http://minecraftforum.net/topic/1582727-/

### Licensing

AbyssalCraft
Copyright (c) 2012 - 2016 Shinoow.
All rights reserved. This program and the accompanying materials
are made available under the terms of the GNU Lesser Public License v3
which accompanies this distribution, and is available at
http://www.gnu.org/licenses/lgpl-3.0.txt

### Support Development

[![](https://s3.amazonaws.com/patreon_public_assets/kaGh5_patreon_name_and_message.png)](https://www.patreon.com/Shinoow)

### Reporting bugs

If you happen to find a bug while playing with the mod, report an issue, with a detailed explanation of what triggered the possible crash (or the bug), and the crash report (either as a gist or a site like pastebin). If you can track down a bug directly in the code, file a pull request pointing out the incorrect code while providing a fix. Make sure you label the issue with the `bug` label (does not apply for a direct PR).

### Suggestions/Enhancements

If you find something you think I should change in the mod that can enhance the experience for you and other users, make an issue and use the `enhancement` label on it. If you, on the other hand, have a suggestion, use the `suggestion` label instead. In both cases, be detailed about the enhancement/suggestion. I have a little list here of things that I need suggestions and feedback on, so if you're up for that, check it out: https://github.com/Shinoow/AbyssalCraft/blob/master/ideas.md

### Contributing

Contributions are welcome. Most contributions might end up being tweaks and improvements, but that's still a contribution.

### Integrations

If you want to import AbyssalCraft into your workspace, add the following to the `repositories` block of your **build.gradle**:

```
repositories {
  ...
  maven {
      // Main location for the maven
      name 'shinoow'
      url 'https://dl.bintray.com/shinoow/maven/'
  }
  maven {
      // Alternate fallback maven mirror
	  name 'ModMaven'
	  url 'modmaven.k-4u.nl'
  }
}
```

Then, in your `dependencies` block, add this:

``` 
dependencies {
  deobfCompile "com.shinoow.abyssalcraft:AbyssalCraft:<mcversion>-<acversion>:<classifier>"
}
```

Where `mcversion` is the Minecraft version you wish to use, `acversion` is the AbyssalCraft version you wish to use, and `classifier` denotes which type of file (eg. "dev" for a deobfuscated jar file, "src" for a jar file with source code or "api" for a zip folder contanining the API package, as of AbyssalCraft 1.9.4-pre-2, this will be a jar file instead of a zip) to download from the maven repo.
As of AbyssalCraft 1.9.4-pre-4, the "dev" and "src" classifiers are no longer used, so either use the "api" classifier for the API jar file, or skip it for a mod jar file with source attachment.
However, this only works for AbyssalCraft **1.9.1.6** (version where I finally set up a maven repo) and up, and only for Minecraft **1.8.9 and up**. If you want to see all the available versions in the maven repo, you can do so over at https://bintray.com/shinoow/maven/abyssalcraft/view#files

If you are looking for previous versions of the mod (or Minecraft versions) to integrate, then check the `dev` branch (as I've uploaded deobfuscated jar files there, along with ones containing source code and zip folders containing the API package). The jar files can be imported into your workspace, and the contents of the zip folder can be extracted into `src/api/java`.
