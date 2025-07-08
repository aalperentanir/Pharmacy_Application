import React, { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { useDispatch } from "react-redux";
import { updateMedicine } from "@/State/Medicine/Action";

const MultiSelect = ({ options, selected, onChange }) => {
  const handleChange = (value) => {
    const newSelected = selected.includes(value)
      ? selected.filter(item => item !== value)
      : [...selected, value];
    onChange(newSelected);
  };

  return (
    <div className="space-y-2">
      {options.map((option) => (
        <div key={option} className="flex items-center space-x-2">
          <Checkbox
            id={option}
            checked={selected.includes(option)}
            onCheckedChange={() => handleChange(option)}
          />
          <Label htmlFor={option}>{option}</Label>
        </div>
      ))}
    </div>
  );
};

const UpdateMedicine = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { id } = useParams();
  const jwt = localStorage.getItem("jwt");
  const [medicine, setMedicine] = useState({
    brand: "",
    description: "",
    quantity: 0,
    suitableSkinTypes: [],
    price: "",
  });

  const skinTypeOptions = ["OILY", "DRY", "COMBINATION", "SENSITIVE", "NORMAL"];

  useEffect(() => {
    if (location.state?.medicine) {
      setMedicine(location.state.medicine);
    }
  }, [location.state]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMedicine((prev) => ({ ...prev, [name]: value }));
  };

  const handleSkinTypesChange = (selectedTypes) => {
    setMedicine(prev => ({ ...prev, suitableSkinTypes: selectedTypes }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(updateMedicine({ id, medicineData: medicine, jwt }));
    navigate("/medicines");
  };

  return (
    <div className="p-5 lg:px-20 max-w-4xl mx-auto">
      <h1 className="font-bold text-3xl pb-5">Update Medicine</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Brand</label>
          <Input
            name="brand"
            value={medicine.brand}
            onChange={handleChange}
            required
            placeholder="brand"
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Description</label>
          <Input
            name="description"
            value={medicine.description}
            onChange={handleChange}
            required
            placeholder="Description"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">Quantity</label>
            <Input
              type="number"
              name="quantity"
              value={medicine.quantity}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Price</label>
            <Input
              name="price"
              value={medicine.price}
              onChange={handleChange}
              required
              placeholder="Price"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Skin Types</label>
          <MultiSelect
            options={skinTypeOptions}
            selected={medicine.suitableSkinTypes}
            onChange={handleSkinTypesChange}
          />
          <p className="text-sm text-muted-foreground mt-1">
            Selected: {medicine.suitableSkinTypes.join(", ") || "None"}
          </p>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => navigate("/medicines")}
          >
            Cancel
          </Button>
          <Button type="submit">Update Medicine</Button>
        </div>
      </form>
    </div>
  );
};

export default UpdateMedicine;