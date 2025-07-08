import React, { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useDispatch } from "react-redux";
import { updateCustomer } from "@/State/Customer/Action";

const UpdateCustomer = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { id } = useParams();
const jwt = localStorage.getItem("jwt");
  const [customer, setCustomer] = useState({
    firstName:"",
    lastName:"",
    gender:"",
    skinType:""
  });

  useEffect(() => {
    if (location.state?.customer) {
      setCustomer(location.state.customer);
    }
  }, [location.state]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCustomer(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
  e.preventDefault();
  dispatch(updateCustomer({ id, customerData: customer, jwt }));
  navigate("/customers");
  };

  return (
    <div className="p-5 lg:px-20 max-w-4xl mx-auto">
      <h1 className="font-bold text-3xl pb-5">Update Customer</h1>
      
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">First Name</label>
          <Input
            name="firstName"
            value={customer.firstName}
            onChange={handleChange}
            required
            placeholder="First Name"
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Last Name</label>
          <Input
            name="lastName"
            value={customer.lastName}
            onChange={handleChange}
            required
            placeholder="Last Name"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">Gender</label>
            <Input
              name="gender"
              value={customer.gender}
              onChange={handleChange}
              required
              placeholder="Gender"
            />
          </div>
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">Skin Type</label>
          <Select
            value={customer.skinType}
            onValueChange={(value) => setCustomer({...customer, skinType: value})}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select skin type" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="OILY">Oily</SelectItem>
              <SelectItem value="DRY">Dry</SelectItem>
              <SelectItem value="COMBINATION">Combination</SelectItem>
              <SelectItem value="SENSITIVE">Sensitive</SelectItem>
              <SelectItem value="NORMAL">Normal</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => navigate("/customers")}
          >
            Cancel
          </Button>
          <Button type="submit">Update Customer</Button>
        </div>
      </form>
    </div>
  );
};

export default UpdateCustomer;