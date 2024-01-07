<template>
	<view class="content">
		<div>
			<div class="server-url">服务器地址:{{url}}</div>
			
			<div class="state-box">
				<div class="server-state">服务器状态:</div>
				<div class="state-icon" :class="{'start': state, 'shutdown': !state}"></div>
			</div>
			
			<div class="button-box">
				<button @click="start">启动</button>
				<button @click="shutdown">关闭</button>
			</div>
		</div>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				url:null,
				state:false,
				password:"",
			};
		},
		onLoad(options) {
			this.url = this.formatUrl(uni.getStorageSync('url'));
			this.password = options.password;
			this.check();
		},
		methods: {
			formatUrl(url){
				// 去掉前面的http/https
				url = url.replace(/^https?:\/\//, '');
				
				// 去掉最后面的所有/
				url = url.replace(/\/+$/, '');
				return url;
			},
			check(){
				uni.request({
				  url: "http://"+this.url+"/check/"+this.password,
				  method: 'POST',
				  success: (res) => {
					var message = res.data.message;
					if(message == "未运行"){
						this.state = false
					}else if(message == "正在运行"){
						this.state = true
					}else{
						//错误
						uni.showToast({
						  title: '连接服务器失败',
						  icon: 'none'
						});
					}
				  },
				  fail: (err) => {
				    console.log(err); // 在控制台输出错误信息
				    // 处理错误信息
				  }
				});
			},
			start(){
				uni.request({
				  url: "http://"+this.url+"/start/"+this.password,
				  method: 'POST',
				  success: (res) => {
					var message = res.data.message;
					console.log("检测");
					this.check();
					uni.showToast({
					  title: message,
					  icon: 'none'
					})
				  },
				  fail: (err) => {
				    console.log(err); // 在控制台输出错误信息
				    // 处理错误信息
				  }
				});
			},
			shutdown(){
				uni.request({
				  url: "http://"+this.url+"/shutdown/"+this.password,
				  method: 'POST',
				  success: (res) => {
					var message = res.data.message;
					console.log("检测");
					this.check();
					uni.showToast({
					  title: message,
					  icon: 'none'
					})
				  },
				  fail: (err) => {
				    console.log(err); // 在控制台输出错误信息
				    // 处理错误信息
				  }
				});
			}
		}
	}
</script>

<style lang="less">
	.content{
		display: flex;
		justify-content: center; /* 水平居中 */
		align-items: center; /* 垂直居中 */
		height: 70vh; /* 设置高度为视口的高度 */
	}
	.start{
		background-color: green;
	}
	.shutdown{
		background-color: red;
	}
	.server-url,.server-state{
		text-align: center;
		font-size: 41rpx;
	}
	.server-url,.state-box{
		padding-top: 20rpx;
	}
	.state-box {
		height: 60rpx;
		display: flex;
		justify-content: center; /* 在主轴上居中 */
		align-items: center; /* 在交叉轴上居中 */
	}
	
	.state-icon {
		margin-top: 4rpx;
		border-radius: 50%;
		width: 45rpx;
		height: 45rpx;
	}
	
	.button-box{
		margin-top: 80rpx;
		button{
			margin: 10px;
		}
	}

</style>
