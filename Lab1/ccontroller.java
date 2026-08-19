import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ccontroller implements ActionListener {
    cmodel model;
    cviews view;

    ccontroller(cmodel model , cviews view){
        this.model = model;
        this.view = view;

        view.add.addActionListener(this);
        view.sub.addActionListener(this);
        view.mul.addActionListener(this);
        view.div.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double a = Double.parseDouble(view.ta.getText());
        double b = Double.parseDouble(view.tb.getText());
        double r = 0;

        if (e.getSource() == view.add) {
            r = model.add(a, b);
        } else if (e.getSource() == view.sub) {
            r = model.sub(a, b);
        } else if (e.getSource() == view.mul) {
            r = model.mul(a, b);
        } else if (e.getSource() == view.div) {
            r = model.div(a, b);
        }


        view.tresult.setText(String.valueOf(r));


    }


}