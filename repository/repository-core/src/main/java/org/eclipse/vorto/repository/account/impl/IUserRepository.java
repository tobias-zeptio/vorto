/**
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.vorto.repository.account.impl;

import java.util.Collection;
import org.eclipse.vorto.repository.domain.Role;
import org.eclipse.vorto.repository.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Edelmann - Robert Bosch (SEA) Pte. Ltd.
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
  /**
   * Finds the user by the specified username
   * 
   * @param username
   * @return
   */
  User findByUsername(String username);

  /**
   * Finds a list of users matching the given partial username.
   * @param partial
   * @return
   */
  @Query("SELECT u from User u WHERE LOWER(u.username) LIKE %?1%")
  Collection<User> findUserByPartial(String partial);

  @Query("SELECT u from User u, TenantUser tu, UserRole r " +
      "WHERE u.id = tu.user.id AND " +
      "tu.id = r.user.id AND " +
      "r.role = :role")
  Collection<User> findUsersWithRole(@Param("role") Role role);

  @Query("select u from User u where u.sysadmin = true")
  boolean isSysadmin(User user);

  @Query("select u from User u where u.username = :name and u.sysadmin = true")
  boolean isSysadmin(String name);

}
