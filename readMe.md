# OpenGL demo

## 1.OpenGL的简单使用
>OpenGL（英语：Open Graphics Library，译名：开放图形库或者“开放式图形库”）是用于渲染2D、3D矢量图形的跨语言、跨平台的应用程序编程接口（API）。
这个接口由近350个不同的函数调用组成，用来绘制从简单的图形比特到复杂的三维景象。学好了OpenGL ，就可以在那些支持OpenGL的机器上正常使用这些接口，在屏幕上看到绘制的结果

1. 继承GLSurfaceView
2. 实现接口 GLSurfaceView.Renderer
3. 编写glsl脚本（shader）

## 2.搭建EGL环境
>EGL 是OpenGL ES和本地窗口系统的接口，不同平台上EGL配置是不一样的，而
 OpenGL的调用方式是一致的，就是说：OpenGL跨平台就是依赖于EGL接口。

 OpenGL整体是一个状态机，通过改变状态就能改变后续的渲染方式，而
 EGLContext（EgL上下文）就保存有所有状态，因此可以通过共享EGLContext
 来实现同一场景渲染到不同的Surface上

1. 获取Egl实例
2. 获取默认的显示设备（就是窗口）
3. 初始化默认显示设备
4. 设置显示设备的属性
5. 从系统中获取对应属性的配置
6. 创建EglContext
7. 创建渲染的Surface
8. 绑定EglContext和Surface到显示设备中