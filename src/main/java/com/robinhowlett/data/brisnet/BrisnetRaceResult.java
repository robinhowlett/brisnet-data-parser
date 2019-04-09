package com.robinhowlett.data.brisnet;

import com.robinhowlett.data.RaceResult;

public class BrisnetRaceResult extends RaceResult {

    private final boolean chuteStart;

    public BrisnetRaceResult(Builder builder) {
        super(builder);
        this.chuteStart = builder.chuteStart;
    }

    public static class Builder extends RaceResult.Builder<Builder> {
        private boolean chuteStart;

        public Builder chuteStart(final boolean chuteStart) {
            this.chuteStart = chuteStart;
            return this;
        }

        @Override
        public BrisnetRaceResult build() {
            return new BrisnetRaceResult(this);
        }
    }
}
