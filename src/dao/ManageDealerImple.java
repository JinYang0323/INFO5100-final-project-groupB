package dao;

import dto.Dealer;
import dto.SearchResult;
import io.UserIO;
import io.UserIOInterface;

import java.util.ArrayList;

public class ManageDealerImple implements ManageDealer{
    private UserIOInterface io;

    public ManageDealerImple() {
        io=new UserIO();
    }

    @Override
    public void updateDealer(Dealer dealer) {
        String data="";
        data="'"+dealer.getName()+"'"+","+"'"+dealer.getUrl()+"'"+","+"'"+dealer.getLocation()+"','"+dealer.getZipcode()+"','"+dealer.getAddress()+"'";
        io.updateData("dealer",dealer.getName(),data);

    }

    @Override
    public ArrayList<Dealer> getDealerByName(String dealerName) {

        ArrayList<String> res=io.getData("SELECT * FROM cloud.dealer WHERE dealername='"+dealerName+"'");
        String[] a=res.get(0).trim().split("~");
        ArrayList<Dealer> dealers=new ArrayList<>();
        Dealer dealer=new Dealer(a[0],a[1],a[2],a[3],a[4]);
        dealers.add(dealer);
        return dealers;
    }

    @Override
    public SearchResult<Dealer> getDealerByLocation(String location, int pageNumber) {
        ArrayList<String> resset;
        int page=0;
        if(pageNumber > 0) {
          resset = io.getData("SELECT SQL_CALC_FOUND_ROWS * FROM cloud.dealer WHERE location='" + location + "' " + "limit " + (pageNumber - 1) * 3 + ", 3");
          page=io.getPage("SELECT FOUND_ROWS()");
          if(page%3==0){
              page=page/3;
          }
          else
              page=page/3+1;

        }
        else {
            resset=io.getData("SELECT * FROM cloud.dealer where location='"+location+"'");
        }

        ArrayList<Dealer> dealers=new ArrayList<>();
        for(String res:resset){
            String[] a=res.trim().split("~");
            Dealer dealer=new Dealer(a[0],a[1],a[2],a[3],a[4]);
            dealers.add(dealer);
        }
        SearchResult<Dealer> searchResult=new SearchResult<Dealer>(page,dealers);
        return searchResult;
    }

    @Override
    public SearchResult<Dealer> getDealerByZipcode(String zipcode,int pageNumber) {
        ArrayList<String> resset;
        int page=0;
        if(pageNumber > 0) {
            resset = io.getData("SELECT SQL_CALC_FOUND_ROWS * FROM cloud.dealer WHERE zipcode='" + zipcode + "'" + "limit " + (pageNumber - 1) * 3 + ", 3");
            page=io.getPage("SELECT FOUND_ROWS()");
            if(page%3==0){
                page=page/3;
            }
            else
                page=page/3+1;
        }
        else {
            resset=io.getData("SELECT * FROM cloud.dealer where zipcode='"+zipcode+"'");
        }

        ArrayList<Dealer> dealers=new ArrayList<>();
        for(String res:resset){
            String[] a=res.trim().split("~");
            Dealer dealer=new Dealer(a[0],a[1],a[2],a[3],a[4]);
            dealers.add(dealer);
        }
        SearchResult<Dealer> searchResult=new SearchResult<Dealer>(page,dealers);
        return searchResult;
    }

    @Override
    public ArrayList<String> getAttributeList(String attribute) {
        ArrayList<String> set=io.getData("select distinct("+attribute+") from dealer;");

        set.remove("null");

        return set;
    }
}
