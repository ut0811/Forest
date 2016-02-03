package forest;

/*
 * Branchクラス
 */
public class Branch extends Object
{
    /**
     * 枝にくっついている親ノードをインスタンスとして束縛する。
     * 良好（2013年7月21日）
     */
    private Node parentNode;
    
    /**
     * 枝にくっついている子ノードをインスタンスとして束縛する。
     * 良好（2013年7月21日）
     */
    private Node childNode;
    
    /**
     * インスタンスを生成して応答する。
     * 引数に親Node,子Nodeを指定し、フィールドに設定する
     * @param parentNode 親Node
     * @param childNode 子Node
     * 良好（2013年7月21日）
     */
    Branch(Node parentNode, Node childNode)
    {
        this.parentNode = parentNode;
        this.childNode = childNode;
    }
    
    /**
     * 親ノードを応答する。
     * @return 親ノード
     * 良好（2013年7月21日）
     */
    public Node getParentNode()
    {
        return this.parentNode;
    }
    
    /**
     * 子ノードを応答する。
     * @return 子ノード
     * 良好（2013年7月21日）
     */
    public Node getChildNode()
    {
        return this.childNode;
    }
}