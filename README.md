# BottomSheetLayout
Simple bottom sheet view for Android

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



### Java/(Kotlin)

#### You can use those methods on the layout :
```
BottomSheetLayout layout = (BottomSheetLayout) findViewById(R.id.bottom_sheet_layout);
...
layout.toogle(); //collapses or expands the view according to the current state
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
