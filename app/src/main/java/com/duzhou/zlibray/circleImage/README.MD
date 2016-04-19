# 关于shape属性的理解错误
最常用的是通过shape为控件设置圆角
今天遇到一个问题：
页面上面有一个ImageView，用shape功能将它设置成圆形
测试成功
通过imageView的src属性，为其设置一张图片。不再圆角。显示的只是图片的样子

错误的理解：通过shape设置为imageview设置圆角之后，这个imageview就是圆形的了
很尴尬
正确的理解：通过shape设置的圆角，其实就是imageview的底图（背景图）。在为其设置显示图片的时候，就不再是圆角
有点拗口，不好描述
## 总结：
通过shape对控件进行圆角设置之后，该控件本身的大小没有变化。只是多了一个圆形的背景图。
当继续为其设置图片之后，shape属性无法影响到图片的显示样式。
## 相关的链接
<?xml version="1.0" encoding="utf-8"?>
    <shape
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:shape=["rectangle" | "oval" | "line" | "ring"] >
        <corners
            android:radius="integer"
            android:topLeftRadius="integer"
            android:topRightRadius="integer"
            android:bottomLeftRadius="integer"
            android:bottomRightRadius="integer" />
        <gradient
            android:angle="integer"
            android:centerX="integer"
            android:centerY="integer"
            android:centerColor="integer"
            android:endColor="color"
            android:gradientRadius="integer"
            android:startColor="color"
            android:type=["linear" | "radial" | "sweep"]
            android:useLevel=["true" | "false"] />
        <padding
            android:left="integer"
            android:top="integer"
            android:right="integer"
            android:bottom="integer" />
        <size
            android:width="integer"
            android:height="integer" />
        <solid
            android:color="color" />
        <stroke
            android:width="integer"
            android:color="color"
            android:dashWidth="integer"
            android:dashGap="integer" />
    </shape>


