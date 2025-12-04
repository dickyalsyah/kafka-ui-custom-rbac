package com.provectus.kafka.ui.model.rbac;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Role {

  String name;
  List<String> clusters = new ArrayList<>();
  List<Subject> subjects = new ArrayList<>();
  List<Permission> permissions = new ArrayList<>();

  public void validate() {
    permissions.forEach(Permission::transform);
    permissions.forEach(Permission::validate);
  }

}
