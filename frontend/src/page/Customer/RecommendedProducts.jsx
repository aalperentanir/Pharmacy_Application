import React, { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { ArrowLeft } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { getRecommendedProducts } from "@/State/Customer/Action";

const RecommendedProducts = () => {
  const { customerId } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  const { 
    recommendedProducts, 
    loading, 
    error,
    currentCustomer 
  } = useSelector((state) => state.customer);

  useEffect(() => {
    if (jwt && customerId) {
      dispatch(getRecommendedProducts({ customerId, jwt }));
    }
  }, [customerId, jwt, dispatch]);

  if (loading) {
    return <div className="p-5">Loading...</div>;
  }

  if (error) {
    return (
      <div className="p-5">
        <div className="text-center py-10">
          <h2 className="text-xl font-semibold">Error</h2>
          <p className="text-muted-foreground mt-2">{error}</p>
          <Button className="mt-4" onClick={() => navigate(-1)}>
            Geri Dön
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="p-5 lg:px-20">
      <Button variant="outline" className="mb-6" onClick={() => navigate(-1)}>
        <ArrowLeft className="h-4 w-4 mr-2" />
        Back to Customer Details
      </Button>

      <Card>
        <CardHeader>
          <CardTitle>
            {currentCustomer 
              ? `Recommended Products for ${currentCustomer.firstName} ${currentCustomer.lastName}`
              : `Recommended Products for Customer with ${customerId}`}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {recommendedProducts && recommendedProducts.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              {recommendedProducts.map((product) => (
                <div key={product.id} className="border p-4 rounded-lg">
                  <h3 className="font-medium">{product.brand}</h3>
                  <p className="text-sm text-muted-foreground">
                    {product.description}
                  </p>
                  <div className="mt-2 flex justify-between items-center">
                    <p className="font-semibold">{product.price} TL</p>
                    <p className="text-sm bg-gray-100 px-2 py-1 rounded">
                      Quantity: {product.quantity}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <p className="text-red-500">The recommended product for this customer can be selected.</p>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default RecommendedProducts;