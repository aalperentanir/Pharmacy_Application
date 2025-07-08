import React, { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { ArrowLeft, User } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { getCustomerById } from "@/State/Customer/Action";

const CustomerDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  const {
    currentCustomer: customer,
    loading,
    error,
  } = useSelector((state) => state.customer);

  useEffect(() => {
    if (jwt && id) {
      dispatch(getCustomerById({ id, jwt }));
    }
  }, [id, dispatch, jwt]);

  if (loading) {
    return <div className="p-5 lg:px-20">Loading...</div>;
  }

  if (error) {
    return (
      <div className="p-5 lg:px-20">
        <div className="text-center py-10">
          <h2 className="text-xl font-semibold">Error</h2>
          <p className="text-muted-foreground mt-2">{error}</p>
          <Button className="mt-4" onClick={() => navigate(-1)}>
            Go Back
          </Button>
        </div>
      </div>
    );
  }

  if (!customer) {
    return (
      <div className="p-5 lg:px-20">
        <div className="text-center py-10">
          <h2 className="text-xl font-semibold">Customer not found</h2>
          <p className="text-muted-foreground mt-2">
            The customer with ID {id} could not be found.
          </p>
          <Button className="mt-4" onClick={() => navigate(-1)}>
            Go Back
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="p-5 lg:px-20">
      <Button variant="outline" className="mb-6" onClick={() => navigate(-1)}>
        <ArrowLeft className="h-4 w-4 mr-2" />
        Back to Dashboard
      </Button>

      <Card>
        <CardHeader className="border-b">
          <div className="flex items-center space-x-4">
            <div className="p-2 rounded-lg bg-primary/10">
              <User className="h-6 w-6 text-primary" />
            </div>
            <div>
              <CardTitle>{`${customer.firstName} ${customer.lastName}`}</CardTitle>
              <p className="text-sm text-muted-foreground">
                Customer ID: {customer.id}
              </p>
            </div>
          </div>
        </CardHeader>
        <CardContent className="pt-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Identity Number
                </h3>
                <p>{customer.identityNumber}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  First Name
                </h3>
                <p>{customer.firstName}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Last Name
                </h3>
                <p>{customer.lastName}</p>
              </div>
            </div>
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Gender
                </h3>
                <p>{customer.gender}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Skin Type
                </h3>
                <p>{customer.skinType}</p>
              </div>
            </div>
          </div>

          <div className="mt-8 flex space-x-4">
            <Button onClick={() => navigate(`/customers/update/${id}`)}>
              Edit Customer
            </Button>
            <Button
              variant="outline"
              onClick={() => navigate(`/customers/order/${id}`)}
            >
              View Orders
            </Button>

            <Button
              variant="outline"
              onClick={() => navigate(`/customers/recommended-product/${id}`)}
            >
              Product Recommendation
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default CustomerDetails;
