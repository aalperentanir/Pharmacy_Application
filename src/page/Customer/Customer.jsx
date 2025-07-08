import React, { use, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import DeleteIcon from '@mui/icons-material/Delete';
import UpdateIcon from '@mui/icons-material/Update';
import { useNavigate } from "react-router-dom";
import ViewListIcon from '@mui/icons-material/ViewList';
import { ArrowLeftIcon, Pencil } from 'lucide-react';
import { ClipboardList, ShoppingCart, FileText, Package, ListOrdered,ClipboardPlus } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { deleteCustomer, getAllCustomers, updateCustomer } from "@/State/Customer/Action";
import { getCustomerOrders } from "@/State/Order/Action";

const Customer = () => {
    const navigate = useNavigate();
    const jwt = localStorage.getItem("jwt");
    const dispatch = useDispatch()
    const {customer} = useSelector(store => store);

      useEffect(() => {
        if (jwt) {
          dispatch(getAllCustomers(jwt));
        }
      }, [dispatch, jwt]);

  const handleRemoveCustomer= (id) => {
        if (jwt && window.confirm("Are you sure you want to delete this customer?")) {
          dispatch(deleteCustomer({ id, jwt }));
        }
  };

const openCustomerOrders = (customer) => {
  dispatch(getCustomerOrders({customerId:customer.id,jwt}))
  navigate(`/customers/order/${customer.id}`);
};

const handleUpdateCustomer = (customer) => {
  dispatch(updateCustomer({ id: customer.id, customerData: customer, jwt }));
  navigate(`/customers/update/${customer.id}`);
};


  return (
    <div className="p-5 lg:px-20">
            <div className="flex items-center gap-4 pb-5">
              <Button
                variant="outline"
                size="icon"
                onClick={() => navigate(-1)}
                className="h-8 w-8"
              >
                <ArrowLeftIcon className="h-4 w-4" />
              </Button>
              <h1 className="font-bold text-3xl">Customers</h1>
            </div>
      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Identity Number</TableHead>
            <TableHead>First Name</TableHead>
            <TableHead>Last Name</TableHead>
            <TableHead>Gender</TableHead>
            <TableHead>Skin Type</TableHead>
            <TableHead>Orders</TableHead>
            <TableHead className="">Update</TableHead>
            <TableHead className="text-right text-red-600">Remove</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {customer.customers?.map((item, index) => (
            <TableRow key={index}>
              <TableCell className="font-medium flex items-center gap-2">
                <span>{item.identityNumber}</span>
              </TableCell>
              <TableCell>{item.firstName}</TableCell>
              <TableCell>{item.lastName}</TableCell>
              <TableCell>{item.gender}</TableCell>
              <TableCell> {Array.isArray(item.skinType) ? item.skinType.join(", ") : item.skinType}</TableCell>
              <TableCell className="">
                <Button
                  variant="ghost"
                  onClick={() => openCustomerOrders(item)}
                  size="icon"
                  className="h-10 w-10"
                >
                  <ClipboardList className="w-6 h-6" />
                </Button>
              </TableCell>
              <TableCell className="">
                <Button
                  variant="ghost"
                  onClick={() => handleUpdateCustomer(item)}
                  size="icon"
                  className="h-10 w-10"
                >
                  <Pencil className="w-6 h-6" />
                </Button>
              </TableCell>

              <TableCell className="text-right">
                <Button
                  variant="ghost"
                  onClick={() => handleRemoveCustomer(item.id)}
                  size="icon"
                  className="h-10 w-10"
                >
                  <DeleteIcon className="w-6 h-6" />
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Customer;