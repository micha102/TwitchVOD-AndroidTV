# TwitchVOD-AndroidTV

An Android TV Application to view Twitch.
Inspired by the Leanback example from Google in Kotlin, and modified it for Twitch VOD no Sub.


## Description

The method is inspired by the method of [besuper/TwitchNoSub](https://github.com/besuper/TwitchNoSub). All credits to it.
Although it can be used on Android phones, it's buggy. So please don't use it on phones.
For PC : An extension is available for Chrome, Firefox ([here](https://github.com/besuper/TwitchNoSub/releases))
For Android phones : Please use Kiwi Browser and use [this extension](https://github.com/meta11ica/mTwitchNoSub-also/releases)
For Android TV : you are in the good place. Download the [APK here](https://github.com/meta11ica/TwitchVOD-AndroidTV/releases)

## Getting Started

#### Add a streamer to your favourite list
Click the Search button, then type your streamer id (username) and click Search.
Long click on the Streamer Card and he will be added to your favourite list.
Otherwise, go to Edit Favourite List and add the streamers by login name.
Hit back and the application will restart with the new list.

#### Remove a streamer to your favourite list
Go to Edit Favourite List, click on the name of the streamer and he will be removed from the list.
Hit back and the application will restart with the new list.


### Installing from releases
You need a File Explorer in your Android TV (for example ES File Explorer). Install it from Play Store.
Please go to [releases section](https://github.com/meta11ica/TwitchVOD-AndroidTV/releases) and download the latest APK version and save it to a thumb drive (flash disk).
In Android TV, go to Setting > Security and Check the option "Unknown sources". (please revert it to Unchecked after installation)
Insert the thumb drive and browse it with ES File Explorer.
Install it, you may need to select "Install anyway" (the package is signed by my own keys which are unknown to Google)

### From Source

#### Dependencies

* All dependencies are specified in build.gradle.kts
  
#### Customize and/or Build 

* Clone or download ZIP and open the project in Android Studio
* Build debug
* Modify the code if you wish to contribute (your contribution is welcome)
* build release

## Help

Feel free to open an issue in case of a problem.

## Version History
* 1.0.54313
    * Replaced the GQL query ChannelVideoShelvesQuery (not working anymore) to FilterableVideoTower_Videos to get VOD streams
* 1.0.36094
    * Fixed live, Edit Favourite Streamers list, bug fixes 
* 1.0.1
    * Initial Release : Very quick and very dirty


## Acknowledgments

Inspiration, code snippets, etc.

* [besuper](https://github.com/besuper/TwitchNoSub)
