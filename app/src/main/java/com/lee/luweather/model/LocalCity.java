package com.lee.luweather.model;

import java.io.Serializable;

/**
 * 通过百度api获取的城市信息
 * Created by Administrator on 2016-1-20.
 */
public class LocalCity implements Serializable{


    /**
     * location : {"lng":92.321999962441,"lat":40.982999976431}
     * formatted_address : 新疆维吾尔自治区巴音郭楞蒙古自治州若羌县
     * business :
     * addressComponent : {"city":"巴音郭楞蒙古自治州","country":"中国","direction":"","distance":"","district":"若羌县","province":"新疆维吾尔自治区","street":"","street_number":"","country_code":0}
     * poiRegions : []
     * sematic_description :
     * cityCode : 86
     */

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public static class ResultEntity {
        private String formatted_address;
        /**
         * city : 巴音郭楞蒙古自治州
         * country : 中国
         * direction :
         * distance :
         * district : 若羌县
         * province : 新疆维吾尔自治区
         * street :
         * street_number :
         * country_code : 0
         */

        private AddressComponentEntity addressComponent;

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public void setAddressComponent(AddressComponentEntity addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public AddressComponentEntity getAddressComponent() {
            return addressComponent;
        }

        public static class AddressComponentEntity {
            private String city;
            private String country;
            private String district;
            private String province;

            public void setCity(String city) {
                this.city = city;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public String getCountry() {
                return country;
            }

            public String getDistrict() {
                return district;
            }

            public String getProvince() {
                return province;
            }
        }
    }
}
