import React, { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { ArrowLeft, Pill } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { getMedicineById } from "@/State/Medicine/Action";

const MedicineDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  const {
    currentMedicine: medicine,
    loading,
    error,
  } = useSelector((state) => state.medicine);

  useEffect(() => {
    if (jwt && id) {
      dispatch(getMedicineById({ id, jwt }));
    }
  }, [id, dispatch, jwt]);

  if (!medicine) {
    return <div>Medicine not found</div>;
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
              <Pill className="h-6 w-6 text-primary" />
            </div>
            <div>
              <CardTitle>{medicine.brand}</CardTitle>
              <p className="text-sm text-muted-foreground">
                Product ID: {medicine.data.id}
              </p>
            </div>
          </div>
        </CardHeader>
        <CardContent className="pt-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Brand
                </h3>
                <p>{medicine.data.brand}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Price
                </h3>
                <p>{medicine.data.price}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Quantity
                </h3>
                <p>{medicine.data.quantity} units</p>
              </div>
            </div>
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Description
                </h3>
                <p>{medicine.data.description}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">
                  Skin Type
                </h3>
                <p>
                  {" "}
                  {(Array.isArray(medicine.data.suitableSkinTypes)
                    ? medicine.data.suitableSkinTypes
                    : [medicine.data.suitableSkinTypes]
                  ).join(", ")}
                </p>
              </div>
            </div>
          </div>

          <div className="mt-8 flex space-x-4">
            <Button onClick={() => navigate(`/medicines/update/${id}`)}>
              Edit Medicine
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default MedicineDetails;
