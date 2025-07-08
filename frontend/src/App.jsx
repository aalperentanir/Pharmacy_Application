import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import Navbar from './page/Navbar/Navbar'
import { Route, Routes } from 'react-router-dom'
import Medicine from './page/Medicine/Medicine'
import UpdateMedicine from './page/Medicine/UpdateMedicine'
import CreateMedicine from './page/CreateMedicine/CreateMedicine'
import Customer from './page/Customer/Customer'
import UpdateCustomer from './page/Customer/UpdateCustomer'
import { CustomerOrders } from './page/Customer/CustomerOrders'
import CreateCustomer from './page/CreateCustomer/CreateCustomer'
import Home from './page/Home/Home'
import CreateOrder from './page/CreateOrder/CreateOrder'
import { Order } from './page/Order/Order'
import Auth from "./page/Auth/Auth"
import MedicineDetails from './page/Medicine/MedicineDetails'
import CustomerDetails from './page/Customer/CustomerDetails'
import { useDispatch, useSelector } from 'react-redux'
import { store } from './State/Store'
import { getUser } from './State/Auth/Action'
import Profile from './page/Profile/Profile'
import RecommendedProducts from './page/Customer/RecommendedProducts'

function App() {
  const [count, setCount] = useState(0)
  const {auth} = useSelector(store => store);
  const dispatch = useDispatch()

  useEffect(()=>{
    dispatch(getUser(localStorage.getItem("jwt")))
  },[auth.token])

  return (
    <>
   {auth.user ?
    <div>
      <Navbar/>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/medicines' element={<Medicine/>}/>
        <Route path="/medicines/update/:id" element={<UpdateMedicine />} />
        <Route path="/medicines/:id" element={<MedicineDetails />} />
        <Route path='/create-medicine' element={<CreateMedicine/>}/>
        <Route path='/customers' element={<Customer/>}/>
        <Route path="/customers/update/:id" element={<UpdateCustomer />} />
        <Route path="/customers/:id" element={<CustomerDetails />} />
        <Route path="/customers/order/:id" element={<CustomerOrders />} />
        <Route path='/create-customer' element={<CreateCustomer/>}/>
        <Route path="/customers/recommended-product/:customerId" element={<RecommendedProducts/>}/>
        <Route path='/orders' element={<Order/>}/>
        <Route path='/create-order' element={<CreateOrder/>}/>
        <Route path='/profile' element={<Profile/>}/>
      </Routes>
    </div>
    : <Auth/>}
    </>
  )
}

export default App
