<template>
  <div>
    <h1>智能起名演示</h1>
    <div v-if="!loggedIn">
      <h2>注册</h2>
      <input v-model="register.username" placeholder="用户名" />
      <input v-model="register.password" placeholder="密码" type="password" />
      <button @click="registerUser">注册</button>
      <h2>登录</h2>
      <input v-model="login.username" placeholder="用户名" />
      <input v-model="login.password" placeholder="密码" type="password" />
      <button @click="loginUser">登录</button>
    </div>
    <div v-else>
      <h2>生成名字</h2>
      <input v-model="surname" placeholder="姓氏" />
      <button @click="generate">生成</button>
      <ul>
        <li v-for="c in candidates" :key="c.name">{{ c.name }} - {{ c.score }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const loggedIn = ref(false);
const surname = ref('张');
const candidates = ref([]);

const register = ref({ username: '', password: '' });
const login = ref({ username: '', password: '' });

async function registerUser() {
  await axios.post('/users/register', register.value).catch(() => {});
}

async function loginUser() {
  const res = await axios.post('/users/login', login.value).catch(() => {});
  if (res && res.status === 200) loggedIn.value = true;
}

async function generate() {
  const res = await axios.post('/name/generate', { surname: surname.value });
  candidates.value = res.data.candidates;
}
</script>
