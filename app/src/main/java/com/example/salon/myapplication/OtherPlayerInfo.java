package com.example.salon.myapplication;

public class OtherPlayerInfo {
        private String id;
        private String email;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return email;
        }

        public OtherPlayerInfo(String id, String email) {

            this.id = id;
            this.email = email;
        }

}
