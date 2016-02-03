package forest;

import java.awt.Point;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import utility.StringUtility;

/**
 * 木を辿って行くForestクラス
 */
public class Forest extends Object
{
    /**
     * 情報を握っているModelのインスタンスを束縛する。
     * 良好（2013年7月21日）
     */
    private ForestModel model;
    
    /**
     * ルートノードのインスタンスたちを束縛する。
     * 良好（2013年7月21日）
     */
    public static ArrayList<Node> rootNodes;
    
    
    /**
     * ノードのインスタンスたちを束縛する。
     * 良好（2013年7月21日）
     */
    private HashMap<Integer,Node> nodes;
    
    /**
     * ブランチのインスタンスたちを束縛する。
     * 良好（2013年7月21日）
     */
    public static ArrayList<Branch> branches;
    
    /**
     * ノード位置の下限を束縛する。
     * 良好（2013年7月21日）
     */
    public static int underLine;
    
	/**
	 * インスタンスを生成して初期化して応答する。
     * 良好（2013年7月21日）
	 */
	public Forest()
    {
        this.model = null;
        this.rootNodes = new ArrayList<Node>();
        this.nodes = new HashMap<Integer,Node>();
        this.branches = new ArrayList<Branch>();
        this.underLine = 0;
	}
    
    /**
     * 指定されたテキストファイルの読み込む。
     * @param aFile テキストファイル名
     * 良好（2013年7月21日）
     */
    public void readText(File aFile) throws IOException
    {
        ArrayList<String> textList = StringUtility.readTextFromFile(aFile);
        System.out.println(textList.size());
        
        int type = 0; //0="trees:" 1="nodes:" 2="branches:"
        for (String tmp : textList)
        {
            tmp = tmp.replaceAll(" ", "");
            System.out.println("= " + tmp);
            if ( tmp.equals("nodes:") ) {type = 1;}
            if ( tmp.equals("branches:") ) {type = 2;}
            this.setTypeData(type, tmp);
        }
        return;
    }
    
    /**
     * それぞれの要素を取り出し束縛する。
     * 良好（2013年7月21日）
     */
    private void setTypeData(int type, String text)
    {
        if ( text.equals("nodes:") ) {return;}
        if ( text.equals("branches:") ) {return;}
        
        if (type == 0)
        {
        }
        else if (type == 1)
        {
            String[] tmp = text.split(",");
            int nodeNumber = Integer.parseInt(tmp[0]);
            String nodeName = tmp[1];
            Node aNode = new Node(nodeNumber, nodeName);
            this.nodes.put(nodeNumber, aNode);
        }
        else if (type == 2)
        {
            String[] tmp = text.split(",");
            Node parentNode = this.nodes.get( Integer.parseInt(tmp[0]) );
            Node childNode = this.nodes.get( Integer.parseInt(tmp[1]) );
            
            parentNode.setChildren(childNode);
            childNode.setParent(parentNode);
            this.nodes.put(Integer.parseInt(tmp[0]), parentNode);
            this.nodes.put(Integer.parseInt(tmp[1]), childNode);
            
            Branch aBranch = new Branch(parentNode, childNode);
            this.branches.add(aBranch);
        }
        else
        {
            System.err.println("===========不正な値です。============");
        }
        
        return;
    }
    
    /**
     * 指定されたモデルをインスタンス変数modelに設定する。
     * @param aModel モデルのインスタンス
     * 良好（2013年7月21日）
     */
    public void setModel(ForestModel aModel)
    {
        this.model = aModel;
        return;
    }
    
    /**
     * ルートノードのインスタンスたちを変数rootNodesに設定する。
     * 良好（2013年7月21日）
     */
    public void setRoot()
    {
        for (Node node : this.nodes.values())
        {
            if(node.getParent() == null){ rootNodes.add(node); }
        }
        return;
    }
    
    /**
     * ノードのインスタンスたちを応答する。
     * @return ArrayList<Node> ノードのインスタンスたち
     * 良好（2013年7月21日）
     */
    public HashMap<Integer,Node> getNodes()
    {
        return this.nodes;
    }
    
    /**
     * ルートノードのインスタンスたちを応答する。
     * @return ArrayList<Node> ルートノードのインスタンスたち
     * 良好（2013年7月21日）
     */
    public ArrayList<Node> getRoot()
    {
        return this.rootNodes;
    }
    
    /**
     * ブランチのインスタンスたちを応答する。
     * @return ArrayList<Branch> ブランチのインスタンスたち
     * 良好（2013年7月21日）
     */
    public ArrayList<Branch> getBranches()
    {
        return this.branches;
    }
    
    /**
	 * 指定秒ごとに木を再起的に探索し、下限の位置をもとに位置を設定していく。
     * また、状態が変わったことをモデルに通知し、再描画する。
     * @param root ルートノードのインスタンス
     * @param aPoint 座標
     * 良好（2013年7月12日）
	 */
    public void visit(Node root, Point aPoint)
    {
        if(root.getVisit() == false){ root.setLocation(aPoint); }
        try
        {
            Thread.sleep(Const.SLEEP_TIME);
        }
        catch (InterruptedException anException)
        {
            System.err.println(anException);
            throw new RuntimeException(anException.toString());
        }
        this.model.changed();
        
        int childY = aPoint.y;
        int count=0;
        for(Node childNode : root.getChildren())
        {
            while(childY <= this.underLine) {childY += 18;} //現在一番下のNodeより下に描くために位置を調整
            this.visit(childNode,new Point(aPoint.x+root.getWidth()+Const.HORIZONTAL_GAP, childY)); //子Nodeを探索
            childNode.setVisit(true); //一度探索したら、座標を変更出来ないようにする
            count++;
            if(root.getChildren().size() > count){
                childY += Const.VERTICAL_GAP + root.getHeight(); //次の子を現在の位置より、2pixel開けた位置に描画
            }
        }
        
        if(this.underLine < childY) {this.underLine = childY;}
        if(root.getVisit() == false){root.setLocation( aPoint.x, (childY+aPoint.y+root.getHeight()) / 2 - (root.getHeight()/2) );}
        if(root.getParent()==null && root.getChildren().size()==1){ root.setLocation( aPoint.x, root.getChildren().get(0).getLocation().y);}
        
        return;
    }
}
