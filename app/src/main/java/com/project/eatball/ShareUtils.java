package com.project.eatball;

public class ShareUtils {

        private String socialmedia;
        private int description;

        public ShareUtils(String socialmedia, int description) {
            this.socialmedia = socialmedia;
            this.description = description;
        }

        public String getSocialmedia() {
            return socialmedia;
        }

        public void setSocialmedia(String socialmedia) {
            this.socialmedia = socialmedia;
        }

        public int getDescription() {
            return description;
        }

        public void setDescription(int description) {
            this.description = description;
        }
    }

