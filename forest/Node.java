package forest;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class Node extends JLabel
{
    /**
     * ノードの番号（数字）を束縛する。
     * 良好（2013年7月21日）
     */
	private int nodeNumber;
    
    /**
     * ノードの名前（文字列）を束縛する。
     * 良好（2013年7月21日）
     */
    private String nodeName;
    
    /**
     * ノードの親インスタンスを束縛する。
     * 良好（2013年7月21日）
     */
    private Node parent;
    
    /**
     * ノードの子インスタンスたちを束縛する。
     * 良好（2013年7月21日）
     */
    private ArrayList<Node> children;
    
    /**
     * 探索済みか否かを束縛する。
     * 良好（2013年7月21日）
     */
    private boolean visit;
    
    /**
	 * インスタンスを生成して応答する。
     * ノードの番号、名前を設定し、このコンポーネントのフォント、テキスト、サイズ、ボーダーを設定する。
     * @param number ノードの番号
     * @param name ノードの名前
     * 良好（2013年7月21日）
     */
    Node(int number, String name)
    {
        this.nodeNumber = number;
        this.nodeName = name;
        this.parent = null;
        this.children = new ArrayList<Node>();
        this.visit = false;
        
		this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setText(this.nodeName);
		this.setBorder(new LineBorder(Color.black, 1));
        this.setSize(this.getPreferredSize());
    }
    
    /**
     * ノードの番号（数字）を応答する。
     * @return ノードの番号
     * 良好（2013年7月21日）
     */
    public int getNumber()
    {
        return this.nodeNumber;
    }
    
    /**
     * ノードの名前（文字列）を応答する。
     * @return ノードの名前
     * 良好（2013年7月21日）
     */
    public String getName()
    {
        return this.nodeName;
    }
    
    /**
     * 親ノードのインスタンスを応答する。
     * @return 親ノードのインスタンス
     * 良好（2013年7月21日）
     */
    public Node getParent()
    {
        return this.parent;
    }
    
    /**
     * 子ノードのインスタンスたちを応答する。
     * @return 子ノードのインスタンスたち
     * 良好（2013年7月21日）
     */
    public ArrayList<Node> getChildren()
    {
        return this.children;
    }
    
    /**
     * 親ノードのインスタンスを設定する。
     * @param aNode 親ノードのインスタンス
     * 良好（2013年7月21日）
     */
    public void setParent(Node aNode)
    {
        this.parent = aNode;
        return;
    }
    
    /**
     * 子ノードのインスタンスを設定する。
     * @param aNode 子ノードのインスタンス
     * 良好（2013年7月21日）
     */
    @SuppressWarnings("unchecked")
    public void setChildren(Node aNode)
    {
        this.children.add(aNode);
        Collections.sort(this.children, new NodeComparator()); //辞書順にソート
        return;
    }
    
    /**
     * 探索状態を設定する。
     * 良好（2013年7月21日）
     */
    public void setVisit(boolean flag)
    {
        this.visit = flag;
        return;
    }
    
    /**
     * 探索済みか応答する。
     * 良好（2013年7月21日）
     */
    public boolean getVisit()
    {
        return this.visit;
    }
}

/**
 * 辞書順ソートのための内部クラス
 */
@SuppressWarnings("rawtypes")
class NodeComparator implements java.util.Comparator {
    /**
     * 辞書順ソートするコンパレータ
     * @param node1 比較するNode
     * @param node2 比較するNode
     * @return 大小関係を表す値
     * 良好（2013年7月21日）
     */
	public int compare(Object node1, Object node2) {
		//               + (x > y)
		// compare node1 node2 = 0 (x = y)
		//               - (x < y)
        return ((Node) node1).getName().compareTo(((Node) node2).getName());
	}
}