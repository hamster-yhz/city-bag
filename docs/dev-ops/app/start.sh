CONTAINER_NAME=city-bag
IMAGE_NAME=crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com/genshin_op/city-bag:latest
PORT=8848

echo "容器部署开始 ${CONTAINER_NAME}"

# 停止容器
docker stop ${CONTAINER_NAME}

# 删除容器
docker rm ${CONTAINER_NAME}

docker rmi ${IMAGE_NAME}

# 启动容器
docker run --name ${CONTAINER_NAME} \
-p ${PORT}:${PORT} \
-d ${IMAGE_NAME}

echo "容器部署成功 ${CONTAINER_NAME}"

docker logs -f ${CONTAINER_NAME}