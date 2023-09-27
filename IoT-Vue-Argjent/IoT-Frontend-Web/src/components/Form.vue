<script>
import { ref } from 'vue';
import { useAuthStore } from '@/stores';
import { useRouter } from 'vue-router';
import { useAlertStore } from '@/stores';
import { useAssetStore } from '@/stores';
import UtilityLabels from '../entity/map/UtilityLabels';
import Asset from '../entity/Asset';
import { dateFormatterForPost } from '../helpers/dataUtils';


/*
let usr;
let pwd;

const select = new Asset();


//contiene l'oggetto form
const form = ref(null);

//router
const router = useRouter();

//authStore
const store = useAuthStore();
const alertStore = useAlertStore();

const onLogin = () => {
    if (form.value.checkValidity() === false) {
        form.value.reportValidity();
        return;
    }
    store.login({ usr, pwd })
        .then(_ => router.push('/'))
        .catch(error => {
            console.log(error);
            alertStore.error('Autenticazione fallita!');
        })
}

const onInsert = () => {
  console.log(data)
}
*/

export default{
  data() {
    return {
      data: new Asset()
    };
  },
  methods: {
    onInsert() {
      const store = new useAssetStore();
      this.data.activation.value = dateFormatterForPost(this.data.activation.value);
      const dataConverted = this.data.createJSONFromAsset(this.data)
      console.log(dataConverted)
      store.createAsset(dataConverted);
      //this.$router.go(0);
    },
    traduci(value) {
      return UtilityLabels.traduci(value);
    }
  },
};

</script>


<template>
    <div id="insert">
        <div class="insert-card">
      
          <div class="card-title">
            <h1>INSERISCI NUOVO ASSET</h1>
          </div>
      
          <div class="content">
            <form method="post" ref="form">
     
                <template v-for="(item, key, index) in data" :key="index">           
                    <label class="label" for="">{{ traduci(key) }}</label>
                    <input v-model="item.value" class="input is-primary" v-if="item.constructor.name != 'select'" 
                           :type="item.constructor.name"
                           :value="item.value"/>
                    <select v-else 
                           :name="item.constructor.name"
                           v-for="(values, key, index2) in item" :key="index2">
                           <option v-for="(value, key, index3) in values" :key="index3"
                           :value="value">
                           {{value}}
                           </option>
                    </select>
                  </template>
             <button class="btn btn-primary" @click.prevent="onInsert()">INSERT</button>
            </form>
          </div>
        </div>
      </div>
</template>


<style setup lang="scss">
$primary:      hsl(171, 100%, 41%);
$grey-darker:  hsl(0, 0%, 21%);
$grey-dark:    hsl(0, 0%, 29%);
$grey:         hsl(0, 0%, 48%);
$grey-light:   hsl(0, 0%, 71%);
$grey-lighter: hsl(0, 0%, 86%);


#insert {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 100vh;

	.insert-card {
		background: #fff;
		width: 24rem;
		box-shadow: 0 0 7px 0 rgba(0, 0, 0, 0.11);

		.card-title {
			background-color: darken($primary, 5%);
			padding: 2rem;

			h1 {
				color: #fff;
				text-align: center;
				font-size: 1.2rem;
			}
		}

		.content {
			padding: 3rem 2.5rem 5rem;
		}

		#email, #password {
			display: block;
			width: 100%;
			font-size: 1rem;
			margin-bottom: 1.75rem;
			padding: 0.25rem 0;
			border: none;
			border-bottom: 1px solid $grey-lighter;
			transition: all .5s;

			&:hover {
				border-color: $grey;
			}

			&:active, &:focus {
				border-color: $primary;
			}
		}

		.checkbox {
			color: $grey-light;
			font-size: 0.8rem;

			span {
				margin-left: 0.5rem;
			}
		}

		a {
			font-size: 0.8rem;
		}

		.options {
			color: $grey-light;
			margin-bottom: 1.5rem;
		}

		button {
			cursor: pointer;
			font-size: 1.2rem;
			color: $primary;
			border-radius: 4rem;
			display: block;
			width: 100%;
			background: transparent;
			border: 2px solid $primary;
			padding: 0.9rem 0 1.1rem;
			transition: color .5s, border-color .5s;
      margin: 2.5rem 0 0 0;

			&:hover, &:focus {
				color: darken($primary, 10%);
				border-color: darken($primary, 10%);
			}

			&:active {
				transform: translateY(1px);
			}
		}
	}
}

label {
	cursor: pointer;
}

.regular-checkbox {
	display: none;
}

.regular-checkbox + label {
	background-color: #fafafa;
	border: 1px solid $grey-lighter;
	box-shadow: 0 1px 2px rgba(0,0,0,0.05);
	padding: 7px;
	border-radius: 3px;
	display: inline-block;
	position: relative;
}

.regular-checkbox:checked + label {
	background-color: #e9ecee;
}

.regular-checkbox:checked + label:after {
	content: '\2714';
	font-size: 11px;
	position: absolute;
	top: 0;
	left: 3px;
	color: $grey-light;
}

input:focus,
select:focus,
textarea:focus,
button:focus {
	outline: none;
}
</style>