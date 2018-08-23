package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material {

@SerializedName("material_id")
@Expose
private String materialId;
@SerializedName("material_name")
@Expose
private String materialName;

public String getMaterialId() {
return materialId;
}

public void setMaterialId(String materialId) {
this.materialId = materialId;
}

public String getMaterialName() {
return materialName;
}

public void setMaterialName(String materialName) {
this.materialName = materialName;
}

}
