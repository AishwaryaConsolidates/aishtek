package com.aishtek.aishtrack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.aishtek.aishtrack.beans.NameId;

public class BankAccountDAO extends BaseDAO {

  public static ArrayList<HashMap<String, String>> searchFor(Connection connection)
      throws SQLException {
    String sql = "SELECT ba.id, ba.name, ba.branch from bank_accounts where ba.deleted = 0 ";

    PreparedStatement statement = connection.prepareStatement(sql);
    ResultSet result = statement.executeQuery();

    ArrayList<HashMap<String, String>> bankAccounts =
        new ArrayList<HashMap<String, String>>();
    while (result.next()) {
      HashMap<String, String> hashMap = new HashMap<String, String>();
      hashMap.put("id", "" + result.getInt(1));
      hashMap.put("name", result.getString(2));
      hashMap.put("branch", result.getString(3));
      bankAccounts.add(hashMap);
    }
    return bankAccounts;
  }

  public static ArrayList<NameId> forSupplier(Connection connection, int supplierId)
      throws SQLException {
    String sql =
        "SELECT ba.id, ba.name, ba.branch from bank_accounts ba, supplier_bank_accounts sba where ba.deleted = 0 and ba.id = sba.bank_account_id and sba.supplier_id = ? ";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, supplierId);
    ResultSet result = statement.executeQuery();

    ArrayList<NameId> bankAccounts = new ArrayList<NameId>();
    while (result.next()) {
      bankAccounts.add(
          new NameId(result.getInt(1), (result.getString(2) + " (" + result.getString(3) + ")")));
    }
    return bankAccounts;
  }

  public static ArrayList<NameId> getCurrentAishwaryaBankAccounts(Connection connection)
      throws SQLException {
    String sql =
        "SELECT ba.id, ba.name, ba.branch from bank_accounts ba, aishwarya_bank_accounts aba "
            + "where ba.deleted = 0 and ba.id = aba.bank_account_id and aba.start_date <= NOW() and aba.end_date >= NOW()";

    PreparedStatement statement = connection.prepareStatement(sql);
    ResultSet result = statement.executeQuery();

    ArrayList<NameId> bankAccounts = new ArrayList<NameId>();
    while (result.next()) {
      bankAccounts.add(
          new NameId(result.getInt(1), (result.getString(2) + " (" + result.getString(3) + ")")));
    }
    return bankAccounts;
  }

  public static ArrayList<NameId> getAddressesFor(Connection connection, int bankAccountId)
      throws SQLException {
    String sql =
        "SELECT a.id, a.area, a.city from bank_account_addresses baa, addresses a where baa.address_id = a.id and baa.bank_account_id = ? ";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, bankAccountId);
    ResultSet result = statement.executeQuery();

    ArrayList<NameId> bankAccountAddresses = new ArrayList<NameId>();
    while (result.next()) {
      bankAccountAddresses.add(
          new NameId(result.getInt(1), (result.getString(2) + " (" + result.getString(3) + ")")));
    }
    return bankAccountAddresses;
  }
}
