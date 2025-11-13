package com.example.tasktracker.constants;

public interface UrlConstants {
   String USER = "/user";

   String REGISTER = "/REGISTER";

   String LOGIN = "/login";

   String TASK = "/task";

   String ADD_TASK = "/{userId}/addTask";

   String GET_TASKS_LIST= "/{userId}/getTasksByUser";

   String COMPLETE_TASK = "/complete/{taskId}/{userId}";

   String DELETE_TASK = "/{userId}/deleteTask/{taskId}";
}

