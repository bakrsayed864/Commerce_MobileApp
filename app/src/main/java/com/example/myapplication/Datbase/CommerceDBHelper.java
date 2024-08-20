package com.example.myapplication.Datbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.Customer;
import com.example.myapplication.Models.Order;
import com.example.myapplication.Models.OrderDetails;
import com.example.myapplication.Models.Product;
import com.example.myapplication.Models.UnconfirmedProducts;

public class CommerceDBHelper extends SQLiteOpenHelper {
    private static String databaseName = "commerceDatabase";
    SQLiteDatabase commerceDatabase;
    public CommerceDBHelper(Context context) {
        super(context, databaseName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer (custid integer primary key autoincrement,custname text not null,username text not null,password text not null,gender text,birthdate text,job text)");
        db.execSQL("create table orders (orderid integer primary key autoincrement,orderdate text,address text,cust_id integer,foreign key (cust_id) references customer(custid))");
        db.execSQL("create table order_details(ordid integer not null, prodid integer not null,quantity integer,rate integer,primary key(ordid,prodid), foreign key (ordid) references orders(orderid),foreign key(prodid) references product(prodid))");
        db.execSQL("create table category(catid integer primary key autoincrement,catname text)");
        db.execSQL("create table product(prodid integer primary key autoincrement, prodname text not null,price real not null,quantity integer,cat_id integer,image blob,foreign key(cat_id) references category(catid))");
        db.execSQL("create table unconfirmes_orders(id integer primary key autoincrement, proid integer ,prodname text not null,price real not null,quantity integer,userid integer,image blob)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists order_details");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists unconfirmes_orders");
        onCreate(db);
    }
    public void makeOrder(Order order){
        commerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("orderdate",order.getOrderDate().toString());
        row.put("address",order.getOrderAddress());
        row.put("cust_id",order.getCustId());
        commerceDatabase.insert("orders",null,row);
        commerceDatabase.close();
    }
    public void insertOrderDetails(OrderDetails orderDetails){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select max(orderid) from orders",null);
        if(cursor!=null)
            cursor.moveToFirst();
        ContentValues row2=new ContentValues();
        row2.put("ordid",Integer.parseInt(cursor.getString(0)));
        row2.put("prodid",orderDetails.getProId());
        row2.put("quantity",orderDetails.getQuantity());
        row2.put("rate",orderDetails.getRate());
        commerceDatabase=getWritableDatabase();
        commerceDatabase.insert("order_details",null,row2);
        cursor.close();
        commerceDatabase.close();
    }
    public void insertUnconfirmedProduct(UnconfirmedProducts product){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select * from unconfirmes_orders where proid='"+product.getId()+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("proid",product.getId());
        row.put("prodname",product.getName());
        row.put("price",product.getPrice());
        row.put("quantity",product.getQuantity());
        row.put("image",product.getImage());
        row.put("userid",product.getUserid());
        if(cursor.getCount()==0)
            commerceDatabase.insert("unconfirmes_orders",null,row);
        cursor.close();
        commerceDatabase.close();
    }
    public boolean updateQuantity(int quant,int id){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select quantity from product where prodid='"+id+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("quantity",quant);
        if(cursor.getInt(0)>=quant){
            commerceDatabase.update("unconfirmes_orders",row,"proid='"+id+"'",null);
            commerceDatabase.close();
            cursor.close();
            return true;
        }
        else {
            commerceDatabase.close();
            return false;
        }
    }
    public  void cartEmpty(){
        commerceDatabase=getWritableDatabase();
        commerceDatabase.delete("unconfirmes_orders",null,null);
        commerceDatabase.close();
    }
    public void removeUnconfirmedProduct(int id){
        commerceDatabase=getWritableDatabase();
        commerceDatabase.delete("unconfirmes_orders","proid='"+id+"'",null);
        commerceDatabase.close();
    }
    public Cursor getUnconfirmedProducts(int id){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select * from unconfirmes_orders where userid='"+id+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        return cursor;
    }
    public void newUser(Customer customer) {
        ContentValues row = new ContentValues();
        row.put("custname", customer.getName());
        row.put("username", customer.getUsername());
        row.put("password", customer.getPassword());
        row.put("gender", customer.getGender());
        row.put("birthdate", customer.getBirthDate());
        row.put("job", customer.getJob());
        commerceDatabase = getWritableDatabase();
        commerceDatabase.insert("customer", null, row);
        commerceDatabase.close();
    }
    public int getUserId(String username){//name
        commerceDatabase=getReadableDatabase();
        String[]args={username};
        Cursor cursor=commerceDatabase.rawQuery("select custid from customer where username=?",args);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            commerceDatabase.close();
            return Integer.parseInt(cursor.getString(0));
        }
        commerceDatabase.close();
        cursor.close();
        return -1;
    }
    public void newProduct(Product product){
        ContentValues row = new ContentValues();
        row.put("prodname",product.getName());
        row.put("price",product.getPrice());
        row.put("quantity",product.getQuantity());
        row.put("cat_id",product.getCatId());
        row.put("image",product.getImage());
        commerceDatabase=getWritableDatabase();
        commerceDatabase.insert("product",null,row);
        commerceDatabase.close();
    }
    public Cursor getAllProducts(){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select * from product where quantity >'"+0+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        return cursor;
    }
    //fetch all users
    public  Cursor allUsers(){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select username from customer",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();
        return cursor;
    }
    public void updateProductQuantity(int id,int qunat){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select quantity from product where prodid='"+id+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        int newQuantity=cursor.getInt(0)-qunat;
        ContentValues row=new ContentValues();
        row.put("quantity",newQuantity);
        commerceDatabase=getWritableDatabase();
        commerceDatabase.update("product",row,"prodid='"+id+"'",null);
        commerceDatabase.close();
    }
    public  Cursor getProductByName(String name){
        commerceDatabase=getReadableDatabase();
        String[]fields={"prodid","prodname","price","quantity","cat_id","image"};
        Cursor cursor= commerceDatabase.rawQuery("select distinct * from product where prodname like ?", new String[]{"%"+name+"%"},null);
        if (cursor!=null) {
            cursor.moveToFirst();
        }
        //commerceDatabase.close();
        return cursor;
    }
    public Cursor getProductsByCategory(int categoryId){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=  commerceDatabase.rawQuery("select * from product where cat_id = '"+categoryId+"'",null);
        if (cursor!=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getProductById(String id){
        commerceDatabase=getReadableDatabase();
        String []args={id};
        Cursor cursor=  commerceDatabase.rawQuery("select * from product where prodid ='"+id+"'",null);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }
    public void deleteProduct(int id){
        commerceDatabase=getWritableDatabase();
        commerceDatabase.delete("product","prodid='"+id+"'",null);
        commerceDatabase.close();
    }
    public void editProduct(int id,String newname,double newprice,int newquantity){
        commerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("prodname",newname);
        row.put("price",newprice);
        row.put("quantity",newquantity);
        commerceDatabase.update("product",row,"prodid='"+id+"'",null);
        commerceDatabase.close();

    }
    public void newCategory(Category category){
        ContentValues row=new ContentValues();
        row.put("catname",category.getName());
        commerceDatabase=getWritableDatabase();
        commerceDatabase.insert("category",null,row);
        commerceDatabase.close();
    }
    public Cursor getCategory(){
        commerceDatabase=getReadableDatabase();
        String []args={"catid","catname"};
        Cursor cursor= commerceDatabase.query("category",args,null,null,null,null,null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
        }
        commerceDatabase.close();
        return cursor;
    }
    public String getCatId(String name){
        commerceDatabase=getReadableDatabase();
        String[]args={name};
        Cursor cursor=commerceDatabase.rawQuery("select catid from category where catname =?",args);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            commerceDatabase.close();
            return cursor.getString(0);
        }
        commerceDatabase.close();
        cursor.close();
        return null;

    }
    public void deleteCategory(int id){
        commerceDatabase=getWritableDatabase();
        commerceDatabase.delete("product","cat_id='"+id+"'",null);
        commerceDatabase.delete("category","catid='"+id+"'",null);
        commerceDatabase.close();
    }
    public boolean checkUserExist(String username) {
        commerceDatabase = getReadableDatabase();
        String[] User = {"username"};
        String[] args = {username};
        Cursor cursor = commerceDatabase.rawQuery("select * from customer where username like ?", args, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        commerceDatabase.close();
        if (cursorCount > 0)
            return true;
        return false;
    }
    public boolean logIn(String userName, String password) {
        commerceDatabase = getReadableDatabase();
        String[] arg = {userName};
        Cursor cursor = commerceDatabase.rawQuery("select username,password from customer where username like ?", arg, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count > 0) {
            if (cursor.getString(1).equals(password)) {
                return true;
            }
        }
        return false;
    }
    public Cursor AllUsersReportInfo(String date){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("SELECT c.custname,o.address,p.prodname,od.quantity,o.orderdate,od.rate FROM orders o, customer c, order_details od, product p WHERE o.cust_id=c.custid and o.orderid=od.ordid and p.prodid=od.prodid and o.orderdate='"+date+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();;
        return cursor;
    }
    public Cursor AllUsersReportInfoWithNoDate(String userName){
        commerceDatabase=getReadableDatabase();
        Cursor cursor;
        if(userName=="All")
            cursor=commerceDatabase.rawQuery("SELECT c.custname,o.address,p.prodname,od.quantity,o.orderdate,od.rate FROM orders o, customer c, order_details od, product p WHERE o.cust_id=c.custid and o.orderid=od.ordid and p.prodid=od.prodid",null);
        else
            cursor=commerceDatabase.rawQuery("SELECT c.custname,o.address,p.prodname,od.quantity,o.orderdate,od.rate FROM orders o, customer c, order_details od, product p WHERE o.cust_id=c.custid and o.orderid=od.ordid and p.prodid=od.prodid and c.username='"+userName+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();;
        return cursor;
    }
    public Cursor SpecificUserReportInfo(String date,String name){
        commerceDatabase=getReadableDatabase();
        //"SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";
        Cursor cursor=commerceDatabase.rawQuery("SELECT c.custname,o.address,p.prodname,od.quantity,o.orderdate,od.rate FROM orders o, customer c, order_details od, product p WHERE o.cust_id=c.custid and o.orderid=od.ordid and p.prodid=od.prodid and c.username='"+name+"' and o.orderdate='"+date+"'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();;
        return cursor;
    }
    public void editCategory(int catId, String newName){
        commerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("catname",newName);
        commerceDatabase.update("category",row,"catid='"+catId+"'",null);
       commerceDatabase.close();
    }
    public String getPassword(String name,String userName){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select password from customer where custname='"+name+"' and username='"+userName+"'",null);
        if (cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();
        if(cursor.getCount()>0)
            return cursor.getString(0);
        return "";
    }
    public Cursor productsSales(){
        commerceDatabase=getReadableDatabase();
        Cursor cursor=commerceDatabase.rawQuery("select p.prodname,Sum(od.quantity) from order_details od, product p where od.prodid=p.prodid group by p.prodname",null);
        if(cursor!=null)
            cursor.moveToFirst();
        commerceDatabase.close();
        return cursor;
    }
    public void DeleteAccount(String userName){
        commerceDatabase=getWritableDatabase();
        commerceDatabase.delete("customer","username='"+userName+"'",null);
        commerceDatabase.close();

    }
}