#!/bin/bash
#判断服务器各个服务调用是否连通
#遍历文件夹，过滤出txt文件，curl请求每个域名，判断是否通
echo -e "<<<<<<<<<<开始校验服务是否启动>>>>>>>>" > judge_network.sh.log
path=$(cd `dirname $0`;pwd)
dir=`ls $path | find . -name '*.txt'`  
for i in $dir;
do
    echo -e "======== 处理文件:$i,有误域名如下:========" >> judge_network.sh.log
    domains=`cat $i`
    for j in $domains;
    do
       echo $j 
       code=`curl -o /dev/null -s -w %{http_code} $j`
       if [ $code != 200 ];then
           echo $j >> judge_network.sh.log
       fi
     done
done
