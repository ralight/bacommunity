# Bacommunity

Bacommunity is an Android app that plays brief audio clips of Jono Bacon mentioning "community". 

https://play.google.com/store/apps/details?id=org.atchoo.bacommunity

## Contributing new clips

I will happily take contributions of new audio clips. There are some rules for inclusion:

* It must be Jono speaking
* Clear, good quality audio only please
* You must have the right to use the audio
* Clean language only
* *Ideally* the audio should include the word "community"
* Remixes are ok, as long as you're not incriminating Jono

Once you have the audio ready, save it as an mp3 in `app/src/main/res/raw/newclip.mp3`.

Now create `app/src/main/res/drawable/newclip.xml`, using one of the existing xml files as an example. This defines the animation. You can use the files jb0.jpg to jb5.jpg (and jbw.jpg) as different frames for the animation. You'll need to figure out the timings for the animation once everything else is in place using the debugger on the VM or your phone.

In MainActivity.java, around line 76 you should add a new entry to the `clips[][]` variable:

> (R.raw.newclip, R.drawable.newclip),

With this in place, you should be able to build a new apk and test it to get the animation synchronised with the audio.

Once you are happy with the audio and animation, it is important to copy newclip.xml to all of `drawable-xhdpi`, `drawable-xxhdpi` and `drawable-xxxhdpi`.

## Colophon / Licensing

Bacommunity is released under the GPL version 3 or later.

The image of Jono used with permission.

Current audio is taken from a variety of [Bad Voltage](http://badvoltage.org/) episodes. The Bad Voltage shows are released under the Creative Commons Attribution Share-Alike license.