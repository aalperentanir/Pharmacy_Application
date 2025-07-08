import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import DeleteIcon from "@mui/icons-material/Delete";
import { ArrowLeftIcon, Pencil } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { deleteeMedicine, getAllMedicines, updateMedicine } from "@/State/Medicine/Action";

const skinTypes = ["OILY", "NORMAL", "DRY", "SENSITIVE"];

const Medicine = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const { medicine } = useSelector(store => store);

  useEffect(() => {
    if (jwt) {
      dispatch(getAllMedicines(jwt));
    }
  }, [dispatch, jwt]);

  const [selectedTypes, setSelectedTypes] = useState([]);

  const handleRemoveMedicine = (id) => {
            if (jwt && window.confirm("Are you sure you want to delete this medicine?")) {
              dispatch(deleteeMedicine({ id, jwt }));
            }
  };

  const handleUpdateMedicine = (medicine) => {
    dispatch(updateMedicine({ medicineData: medicine, jwt, id:medicine.id }));
    navigate(`/medicines/update/${medicine.id}`);
  };

  const handleTypeChange = (type) => {
    if (selectedTypes.includes(type)) {
      setSelectedTypes(selectedTypes.filter((t) => t !== type));
    } else {
      setSelectedTypes([...selectedTypes, type]);
    }
  };

  const filteredMedicines =
    selectedTypes.length === 0
      ? medicine.medicines || []
      : (medicine.medicines || []).filter((item) =>
          item.suitableSkinTypes.some((type) => selectedTypes.includes(type))
        );

  return (
    <div className="p-5 lg:px-20">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center pb-5 gap-4">
              <div className="flex items-center gap-4 pb-5">
                <Button
                  variant="outline"
                  size="icon"
                  onClick={() => navigate(-1)}
                  className="h-8 w-8"
                >
                  <ArrowLeftIcon className="h-4 w-4" />
                </Button>
                <h1 className="font-bold text-3xl">Medicines</h1>
              </div>
        <div className="flex flex-wrap gap-4">
          {skinTypes.map((type) => (
            <label key={type} className="flex items-center gap-2">
              <input
                type="checkbox"
                value={type}
                checked={selectedTypes.includes(type)}
                onChange={() => handleTypeChange(type)}
              />
              {type}
            </label>
          ))}
        </div>
      </div>

      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Id</TableHead>
            <TableHead className="py-5">Brand</TableHead>
            <TableHead>Description</TableHead>
            <TableHead>Quantity</TableHead>
            <TableHead>Skin Type</TableHead>
            <TableHead>Price</TableHead>
            <TableHead>Update</TableHead>
            <TableHead className="text-right text-red-600">Remove</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {filteredMedicines.map((item, index) => (
            <TableRow key={index}>
              <TableCell className="font-medium">{item.id}</TableCell>
              <TableCell>{item.brand}</TableCell>
              <TableCell>{item.description}</TableCell>
              <TableCell>{item.quantity}</TableCell>
              <TableCell>{item.suitableSkinTypes?.join(", ") || ""}</TableCell>
              <TableCell>{item.price}</TableCell>
              <TableCell>
                <Button
                  variant="ghost"
                  onClick={() => handleUpdateMedicine(item)}
                  size="icon"
                  className="h-10 w-10"
                >
                  <Pencil className="w-6 h-6" />
                </Button>
              </TableCell>
              <TableCell className="text-right">
                <Button
                  variant="ghost"
                  onClick={() => handleRemoveMedicine(item.id)}
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

export default Medicine;