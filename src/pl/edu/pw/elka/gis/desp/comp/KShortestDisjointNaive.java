package pl.edu.pw.elka.gis.desp.comp;


import pl.edu.pw.elka.gis.desp.io.CompOutputFormatter;
import pl.edu.pw.elka.gis.desp.model.DirectedEdge;
import pl.edu.pw.elka.gis.desp.model.WeightedDiagraph;

public class KShortestDisjointNaive implements KShortestDisjoint {

    private final WeightedDiagraph org;
    private final int src;
    private final int dst;
    private EdgedDisjointedPaths paths;


    public KShortestDisjointNaive(WeightedDiagraph wdg, int src, int dst) {
        this.src = src;
        this.dst = dst;
        this.org = wdg;

    }


    private void removePath(WeightedDiagraph wdg, Path p) {
        if(p == null) return;
        for(DirectedEdge e: p.getEdges()) {
            wdg.removeEdge(e);
        }
    }

    @Override
    public void run() {
        ShortestPath sp = new DijkstraSP(org, src);

        sp.run();
        Path fpath = sp.pathTo(dst);
        // remove the first path
        WeightedDiagraph clone = org.clone(fpath);
       // removePath(clone, fpath);

        sp.setDiagraph(clone);
        sp.run();
        Path spath = sp.pathTo(dst);

        this.paths = new EdgedDisjointedPaths(fpath, spath);
    }

    @Override
    public EdgedDisjointedPaths getPaths() {
        return paths;
    }
}
