<template>
	<view class="content">
		<div class="form-view">
			<div class="url-box">
				<input class="url-input" type="text" placeholder="请输入端口映射端的url" v-model="url">
				<picker @change="handleSelect" :range="reversedUrlArr" v-if="isUrlArr">
					<div class="dropdown"></div>
				</picker>
			</div>
			<div class="password-box">
				<input class="url-input" v-model="password" type="password" placeholder="输入服务器密码">
			</div>
			<button class="url-submit" @click="submitUrl">确定</button>
		</div>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				url:"",
				urlArr:[],
				selectValue:"",
				password:""
			}
		},
		computed: {
			reversedUrlArr() {
				return this.urlArr.slice().reverse();
			  },
			isUrlArr() {
				if (Array.isArray(this.urlArr) && this.urlArr.length === 0) {
				  // urlArr为空数组
				  return false;
				} else {
				  // urlArr不为空数组
				  return true;
				}
			}
		},
		onLoad() {
			this.url = uni.getStorageSync('url');
			var arr = uni.getStorageSync("urlArr");
			if(Array.isArray(arr)){
				this.urlArr = arr;
			}
		},
		methods: {
			//改变选择框
			handleSelect(event){
				this.url = this.urlArr[event.detail.value];
			},
			//修改url
			submitUrl(){
				if(this.url == null || this.url == ""){
					uni.showToast({
					  title: '服务器地址不能为空',
					  icon: 'none'
					});
					return;
				}
				uni.setStorageSync('url', this.url);
				//判断是否存在url 如果存在则删除
				if (this.urlArr.includes(this.url)) {
					this.urlArr = this.urlArr.filter(item => item !== this.url);
				}
				this.urlArr.push(this.url);
				uni.setStorageSync('urlArr',this.urlArr);
				console.log(this.password);
				uni.navigateTo({
				  url: '/pages/prot/prot?password='+this.password
				});
			},
		}
	}
</script>

<style lang="less">
	.dropdown::after {
	  content: "";
	  position: absolute;
	  top: 50%;
	  right: 10px;
	  transform: translateY(-50%);
	  width: 0;
	  height: 0;
	  border-style: solid;
	  border-width: 6px 6px 0 6px;
	  border-color: #000 transparent transparent transparent;
	}

	
	.content {
		display: flex;
		justify-content: center; /* 水平居中 */
		align-items: center; /* 垂直居中 */
		height: 70vh; /* 设置高度为视口的高度 */
		.url-box{
			margin: 0px;
			display: flex;
			justify-content: space-between;
		}
		.url-submit{
			margin-top: 50rpx;
		}
	}
	
	.url-input{
		width: 750rpx;
		border-bottom: 1rpx solid #999;
		padding: 20rpx;
	}
</style>
