
# 普通镜像构建，随系统版本构建 amd/arm
docker build -t crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com/genshin_op/city-bag:1.0 -f ./Dockerfile .

# 阿里云镜像构建
docker login --username=aliyun9345724218 crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com
docker tag sha256:6d96f71a4ecef6131f5136b3d764c7a9e5ae4a425da594bb52810b2ed9f10297 crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com/genshin_op/city-bag:least

docker push crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com/genshin_op/city-bag:least

# 兼容 amd、arm 构建镜像
# docker buildx build --load --platform liunx/amd64,linux/arm64 -t crpi-3z0olgh8c4f93a6d.cn-heyuan.personal.cr.aliyuncs.com/genshin_op/city-bag:1.0 -f ./Dockerfile . --push