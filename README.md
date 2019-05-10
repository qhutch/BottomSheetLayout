[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-BottomSheetLayout-green.svg?style=flat )]( https://android-arsenal.com/details/1/6425 )
[![Android Weekly]( https://img.shields.io/badge/Android%20Weekly-%23283-blue.svg?style=flat )]( http://androidweekly.net/issues/issue-283 )

# BottomSheetLayout
Simple bottom sheet view for Android

![alt text](https://raw.githubusercontent.com/qhutch/BottomSheetLayout/master/sample_gif.gif)


## How to use ?


### Install
Add this to your app build.gradle
```
dependencies {
    ...
    implementation 'com.qhutch.bottomsheetlayout:bottomsheetlayout:0.1.9'
}
```



### XML

And this in your layout xml :
```
<com.qhutch.bottomsheetlayout.BottomSheetLayout
        android:id="@+id/bottom_sheet_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:collapsedHeight="80dp">
        
        ...
        
</com.qhutch.bottomsheetlayout.BottomSheetLayout>
```
And use as any Frame Layout

*collapsedHeight* is the height of the layout when collapsed (default is 0)


### Java/(Kotlin)

#### You can use those methods on the layout :
```
BottomSheetLayout layout = (BottomSheetLayout) findViewById(R.id.bottom_sheet_layout);
...
layout.toggle(); //collapses or expands the view according to the current state
...
layout.collapse(); //collapses the view
...
layout.expand(); //expands the view
...
layout.isExpended(); //return true if the view is expanded
```


#### And if you want to track the progress :
(It may be usefull to animate views at the same time)
```
layout.setOnProgressListener(new BottomSheetLayout.OnProgressListener() {
            @Override
            public void onProgress(float progress) {
                doSomethingWithTheProgress(progress)
            }
        });
```
or in Kotlin :
```
layout.setOnProgressListener { progress -> doSomethingWithTheProgress(progress) }
```
progress is 0 when collapsed and 1 when expanded

## Changelog

##### 0.1.2
- Added possibility to change animation time <br/>
- Compute time of animation based on progress <br/>
- Fixed click detection <br/>

##### 0.1.3
- Reduced minSdk to 14 <br/>

##### 0.1.4
- Fixed scroll bug <br/>

##### 0.1.5
- Override setTranslationY to separate translation from scroll and translation set by user <br/>

##### 0.1.6
- Fixed bug that prevented from scrolling back when end was reached <br/>

##### 0.1.8
- Added set collapsedHeight method
- Updated gradle, kotlin and build sdk

##### 0.1.9
- Migrate to androidX

## TODO list:
:white_large_square: Option to disable touch to drag <br/>


## Contact

Pull requests are more than welcome.

- **Email**: quentin.menini.us@gmail.com
- **Medium**: https://medium.com/@qhutch


# License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
