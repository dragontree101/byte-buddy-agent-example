# byte-buddy-agent-example
简单的使用byte-buddy来做成javaagent, 来修改springboot启动的任务

目前的demo修改了okhttp、redis、thrift的调用方法

添加了扫描指定注解方法的切入

增加了基于jersey的http的方法切入

更新了bytebuddy的版本到1.3.19

增加了使用advice注解的使用，用advice注解完成了基本相同的功能
