# 对开源项目：[-dampView-springingView](https://github.com/Huyamin150/-dampView-springingView)的学习
## 动画实现
（一个水果落下，砸在文字上面，然后弹起来，再落下，再弹起）
在实现的过程中，重新温顾了一下动画的相关实现方法
他是由两个动画集合在一起的（移动动画和旋转动画）
* TranslateAnimation
* RotateAnimation
* 动画集合AnimationSet

遇到一些问题，如下：
## 问题一：
旋转的动画在与位移动画一起使用的时候，无法做到以自己为原点做旋转
经过尝试之后发现，当将他们增加到AnimationSet中的时候，注意他们的先后顺序--先加RotateAnimation，再加TranslateAnimation
## 问题二：
起初没有速度效果，无法模拟出水果下落的效果。查看[-dampView-springingView](https://github.com/Huyamin150/-dampView-springingView)的源码发现可以通过
animatorSet.setInterpolator()来实现
* setInterpolator(Interpolator i)：设置动画的变化速度
* setInterpolator(new AccelerateDecelerateInterpolator())：先加速，后减速
* setInterpolator(new AccelerateInterpolator())：加速
* setInterpolator(new DecelerateInterpolator())：减速
* setInterpolator(new CycleInterpolator())：动画循环播放特定次数，速率改变沿着正弦曲线
* setInterpolator(new LinearInterpolator())：匀速

## 文字的抖动
模拟出文字被砸下去，后又弹起来的效果
同过自定义view里面的canvas.drawTextOnPath(文字的内容, mPath, 0, 0, textPaint);
思路大概是：
> 当水果图片的落下去动画结束的时候，调用这里方法--通过定义PATH来画出一个弯曲的文字
> 中间一个循环的操作，不断的加大文字弯曲的弧度
> 当到达某一个临界点，将文字恢复成曲线
