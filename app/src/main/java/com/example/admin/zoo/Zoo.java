package com.example.admin.zoo;

/**
 * Created by Admin on 8/10/2017.
 */


    public class Zoo {

        private int id;
        private String name;
        private String Location;
        private String Sex;
        private String Diet;

        public Zoo() {}

        public Zoo(String name, String Location, String Sex, String Diet) {
            this.name = name;
            this.Location = Location;
            this.Sex = Sex;
            this.Diet= Diet;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getDiet() {return Diet; }

        public void setName(String name) {
            this.name = name;
        }

        public void setDiet(String diet){ this.Diet = diet;}

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        public String getSex(){ return  Sex;}

        public void setSex(String sex) {this.Sex = sex;}
}
