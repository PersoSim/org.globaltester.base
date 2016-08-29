package org.globaltester.base.ui.editors;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

/**
 * GlobalTester default reconciling strategy adds folding positions according to
 * document partitioning. Each different partition gets a folding position
 * except those with content type IDocument.DEFAULT_CONTENT_TYPE
 * 
 * @author amay
 * 
 */
public class ReconcilingStrategy implements IReconcilingStrategy,
		IReconcilingStrategyExtension {

	private FoldingEditor editor;

	protected IDocument fDocument;

	/** holds the calculated positions */
	protected final ArrayList<Position> fPositions = new ArrayList<Position>();

	protected IProgressMonitor progresMonitor;

	/**
	 * @return Returns the editor.
	 */
	public FoldingEditor getEditor() {
		return editor;
	}

	public void setEditor(FoldingEditor editor) {
		this.editor = editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org
	 * .eclipse.jface.text.IDocument)
	 */
	@Override
	public void setDocument(IDocument document) {
		this.fDocument = document;

	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#
	 * initialReconcile()
	 */
	@Override
	public void initialReconcile() {
		reconcile(new Region(0, fDocument.getLength()));
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.
	 * eclipse.jface.text.reconciler.DirtyRegion,org.eclipse.jface.text.IRegion)
	 */
	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		try {
			IRegion startLineInfo = fDocument
					.getLineInformationOfOffset(subRegion.getOffset());
			IRegion endLineInfo = fDocument
					.getLineInformationOfOffset(subRegion.getOffset()
							+ Math.max(0, subRegion.getLength() - 1));
			if (startLineInfo.getOffset() == endLineInfo.getOffset())
				subRegion = startLineInfo;
			else
				subRegion = new Region(startLineInfo.getOffset(),
						endLineInfo.getOffset()
								+ Math.max(0, endLineInfo.getLength() - 1)
								- startLineInfo.getOffset());

		} catch (BadLocationException e) {
			subRegion = new Region(0, fDocument.getLength());
		}
		reconcile(subRegion);
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.
	 * eclipse.jface.text.IRegion)
	 */
	@Override
	public void reconcile(IRegion region) {
		fPositions.clear();
		int entOffset = region.getOffset() + region.getLength();
		ITypedRegion lastPartion = null;
		for (int curOffset = region.getOffset(); curOffset < entOffset; curOffset++) {
			ITypedRegion curPartition;
			try {
				curPartition = fDocument.getPartition(curOffset);
				if (curPartition != null && !curPartition.equals(lastPartion)) {
					if (!IDocument.DEFAULT_CONTENT_TYPE.equals(curPartition
							.getType())) {
						fPositions.add(new Position(curPartition.getOffset(),
								curPartition.getLength()));
					}
					lastPartion = curPartition;
				}
			} catch (BadLocationException e) {
				// simply don't reconcile BadLocations
			}

		}
		this.updateFoldingStructure();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#
	 * setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		progresMonitor = monitor;
	}

	protected void updateFoldingStructure() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				editor.updateFoldingStructure(fPositions);
			}

		});
	}
}
