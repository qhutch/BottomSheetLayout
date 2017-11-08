# BottomSheetLayout
Simple bottom sheet view for Android

![alt text](https://raw.githubusercontent.com/qhutch/BottomSheetLayout/master/sample_gif.gif)


## How to use ?


### Install
Add this to your app build.gradle
```
dependencies {
    ...
    implementation 'com.qhutch.bottomsheetlayout:bottomsheetlayout:0.1.1'
}
```
or
```
dependencies {
    ...
    compile 'com.qhutch.bottomsheetlayout:bottomsheetlayout:0.1.1'
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
