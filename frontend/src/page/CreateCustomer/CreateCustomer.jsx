import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Label } from "@/components/ui/label";
import { useDispatch } from "react-redux";
import { createCustomer } from "@/State/Customer/Action";

const CreateCustomer = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const [customer, setCustomer] = useState({
    identityNumber: "",
    firstName: "",
    lastName: "",
    gender: "",
    skinType: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCustomer(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(createCustomer({customerData:customer, jwt}))
    navigate("/customers");
  };

  return (
    <div className="p-5 lg:px-20 max-w-4xl mx-auto">
      <h1 className="font-bold text-3xl pb-5">Create New Customer</h1>
      
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <Label htmlFor="identityNumber">Identity Number</Label>
            <Input
              id="identityNumber"
              name="identityNumber"
              value={customer.identityNumber}
              onChange={handleChange}
              required
              placeholder="11-digit identity number"
              maxLength="11"
            />
          </div>

          <div>
            <Label htmlFor="firstName">First Name</Label>
            <Input
              id="firstName"
              name="firstName"
              value={customer.firstName}
              onChange={handleChange}
              required
              placeholder="First name"
            />
          </div>

          <div>
            <Label htmlFor="lastName">Last Name*</Label>
            <Input
              id="lastName"
              name="lastName"
              value={customer.lastName}
              onChange={handleChange}
              required
              placeholder="Last name"
            />
          </div>

          <div>
            <Label htmlFor="gender">Gender</Label>
            <Select
              name="gender"
              value={customer.gender}
              onValueChange={(value) => setCustomer({...customer, gender: value})}
              required
            >
              <SelectTrigger>
                <SelectValue placeholder="Select gender" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="MALE">Male</SelectItem>
                <SelectItem value="FEMALE">Female</SelectItem>
                <SelectItem value="OTHER">Other</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div>
            <Label htmlFor="skinType">Skin Type</Label>
            <Select
              name="skinType"
              value={customer.skinType}
              onValueChange={(value) => setCustomer({...customer, skinType: value})}
              required
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
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => navigate("/customers")}
          >
            Cancel
          </Button>
          <Button type="submit">Create Customer</Button>
        </div>
      </form>
    </div>
  );
};

export default CreateCustomer;